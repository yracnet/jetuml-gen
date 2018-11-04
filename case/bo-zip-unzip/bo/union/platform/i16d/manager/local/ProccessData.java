/**
 *  ____                          _   _       _    __
 * | __ )  __ _ _ __   ___ ___   | | | |_ __ (_) _/_/ _ __
 * |  _ \ / _` | '_ \ / __/ _ \  | | | | '_ \| |/ _ \| '_ \
 * | |_) | (_| | | | | (_| (_) | | |_| | | | | | (_) | | | |
 * |____/ \__,_|_| |_|\___\___/   \___/|_| |_|_|\___/|_| |_|
 *  ____                            _           ____
 * |  _ \ _ __ ___  _   _  ___  ___| |_ ___    / ___|___  _ __ ___
 * | |_) | '__/ _ \| | | |/ _ \/ __| __/ _ \  | |   / _ \| '__/ _ \
 * |  __/| | | (_) | |_| |  __/ (__| || (_) | | |__| (_) | | |  __/
 * |_|   |_|  \___/ \__, |\___|\___|\__\___/   \____\___/|_|  \___|
 *                  |___/
 *
 * Copyright © 2017 - http://union.dev/licence.txt#yracnet
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.union.platform.i16d.manager.local;

import bo.union.platform.i16d.manager.entity.I16dArchiveMapper;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import au.com.bytecode.opencsv.CSVReader;
import bo.union.platform.i16d.manager.entity.I16dArchiveConfig;
import bo.union.platform.i16d.manager.entity.I16dError;

/**
 *
 * @author sipaco
 */
public class ProccessData {

	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin://@192.168.232.116:1521/undesa01";
	static final String USER = "I16D";
	static final String PASS = "I16D1234";
	static Connection conn = null;
	static Statement stmt = null;

	public static void connectToDataBase() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void closeResource() {
		try {
			if (stmt != null) {
				conn.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static boolean existTable(String tableName) {
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet res = meta.getTables(null, null, tableName, new String[]{"TABLE"});
			if (res.next()) {
				return true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	public static void sqlCreateTable(String tableName, List<I16dArchiveMapper> columnList, String proccessType) {
		try {
			if (!"reload".equals(proccessType)) {
				String sql = "CREATE TABLE " + tableName + " (ID_BASE NUMBER NOT NULL, CONTENT_ID NUMBER, VAL_STATE VARCHAR2(50), VAL_DESCRIPTION VARCHAR2(500),";
				for (I16dArchiveMapper am : columnList) {
					sql = sql + am.getColTable() + " " + am.getType();
					if ("VARCHAR2".equals(am.getType())) {
						sql = sql + "(" + am.getSizeValue() + ")";
					}
					sql = sql + ",";
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				stmt.executeUpdate(sql);
				String sqlPublicSyno = "CREATE PUBLIC SYNONYM " + tableName + " for I16D." + tableName;
				stmt.executeUpdate(sqlPublicSyno);
				String sqlGrant = "GRANT SELECT, INSERT, UPDATE, DELETE ON " + tableName + " to I16D_MNG";
				stmt.executeUpdate(sqlGrant);
				sqlCreateSequence(tableName);
				String sqlPublicSynoSeq = "CREATE PUBLIC SYNONYM SEQ_" + tableName + " for I16D.SEQ_" + tableName;
				stmt.executeUpdate(sqlPublicSynoSeq);
				String sqlGrantSeq = "GRANT SELECT ON SEQ_" + tableName + " to I16D_MNG";
				stmt.executeUpdate(sqlGrantSeq);
			}
		} catch (SQLException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void sqlCreateSequence(String tableName) {
		try {
			String sql = "CREATE SEQUENCE SEQ_" + tableName + " INCREMENT BY 1 START WITH 1";
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static Long sqlNextValue(String tableName) throws SQLException {
		Long idTable = -1L;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT SEQ_" + tableName + ".NEXTVAL FROM dual");
			if (rs != null && rs.next()) {
				idTable = rs.getLong(1);
				rs.close();
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return idTable;
	}

	public static void sqlDeleteData(String tableName, Long contendId) {
		String sql = null;
		try {
			if (contendId > 0) {
				sql = "DELETE FROM " + tableName + " WHERE CONTENT_ID = " + contendId;
			} else {
				sql = "DELETE FROM " + tableName;
			}
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void sqlUpdateData(String tableName, Long idBase, ValidatorProcedure value) {
		try {
			String sql = "UPDATE " + tableName + " SET VAL_STATE = '" + value.getValState() + "', VAL_DESCRIPTION = '" + value.getValDescription()
					+ "' WHERE ID_BASE = " + idBase;
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static ValidatorProcedure callValProcedure(String nameProcedure, String nameTable, Long idBase) throws SQLException {
		ValidatorProcedure valPro = new ValidatorProcedure();
		CallableStatement cs = null;
		try {
			String callProc = "{call " + nameProcedure + "(?,?,?,?)}";
			cs = conn.prepareCall(callProc);
			cs.setString(1, nameTable);
			cs.setLong(2, idBase);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.executeQuery();
			valPro.setValState(cs.getString(3));
			valPro.setValDescription(cs.getString(4));
		} finally {
			if (cs != null) {
				cs.close();
			}
		}
		return valPro;
	}

	public static List<I16dError> proccessData(I16dArchiveConfig entity, byte[] contentFile, String contentId, String typeFile, String behavior,
			String proccessType, String nameFile, List<I16dError> listError) {
		Long contId = Long.parseLong(contentId);
		connectToDataBase();
		boolean existTable = existTable(entity.getNameTabla());
		if ("1".equals(behavior)) { // una tabla incremental
			contentId = "";
			if (!existTable) {
				sqlCreateTable(entity.getNameTabla(), entity.getI16dArchiveMapperList(), proccessType);
			}
		} else if ("2".equals(behavior)) { // una tabla por proceso
			sqlCreateTable(entity.getNameTabla() + contentId, entity.getI16dArchiveMapperList(), proccessType);
		} else if ("3".equals(behavior)) { // una tabla reutilizable
			contentId = "";
			if (existTable) {
				sqlDeleteData(entity.getNameTabla(), -1L);
			} else {
				sqlCreateTable(entity.getNameTabla(), entity.getI16dArchiveMapperList(), proccessType);
			}
		}
		if ("reload".equals(proccessType)) {
			sqlDeleteData(entity.getNameTabla() + contentId, contId);
		}
		if ("XLS".equals(typeFile)) {
			return proccessXLS(entity.getXlsHead(), entity.getXlsSheet(), entity.getNameTabla() + contentId, entity.getI16dArchiveMapperList(), contentFile,
					nameFile, contId, entity.getValProcedure(), listError);
		} else if ("CSV".equals(typeFile)) {
			return proccessCSV(entity.getCsvComma(), entity.getCsvHead(), entity.getCsvSplit(), entity.getNameTabla() + contentId,
					entity.getI16dArchiveMapperList(), contentFile, nameFile, contId, entity.getValProcedure(), listError);
		}
		closeResource();
		return null;
	}

	public static List<I16dError> proccessXLS(String xlsHead, String xlsSheet, String tableName, List<I16dArchiveMapper> list, byte[] contentFile,
			String nameFile, Long contentId, String nameProcedure, List<I16dError> listError) {
		PreparedStatement sql_statement = null;
		org.apache.poi.ss.usermodel.Workbook my_xls_workbook = null;
		org.apache.poi.ss.usermodel.Sheet my_worksheet = null;
		try {
			I16dError entityError;
			String jdbc_insert_sql = sqlInsert(tableName, list);
			sql_statement = conn.prepareStatement(jdbc_insert_sql);
			InputStream input_document = new ByteArrayInputStream(contentFile);
			my_xls_workbook = WorkbookFactory.create(input_document);
			my_worksheet = my_xls_workbook.getSheet(xlsSheet);
			if (my_worksheet != null) {
				// DO NOTHING
			} else {
				conn.rollback();
				entityError = buildI16dError(0, "The Sheet " + xlsSheet + " does not exist", "n/a", nameFile);
				listError.add(entityError);
			}
			Iterator<Row> rowIterator = my_worksheet.iterator();
			Row rowHead;
			int rowNumber = 0;
			List<String> listHead = new ArrayList<>();
			if ("SI".equals(xlsHead)) {
				rowHead = rowIterator.next();
				rowNumber++;
				Iterator<Cell> cellHead = rowHead.cellIterator();
				while (cellHead.hasNext()) {
					Cell cell = cellHead.next();
					if (getPosition(list, cell.getStringCellValue()) == -1) {
						entityError = buildI16dError(rowNumber, "La columna [" + cell.getStringCellValue() + "] no existe en la tabla [" + tableName + "] $" + xlsSheet,
								"n/a", nameFile);
						listError.add(entityError);
						return listError;
					}
					listHead.add(cell.getStringCellValue());
				}
				if (list.size() != listHead.size()) {
					entityError = buildI16dError(rowNumber, "El Archivo y la Tabla [" + tableName + "] no tienen la misma cantidad de columnas", "n/a", nameFile);
					listError.add(entityError);
					return listError;
				}
			}
			while (rowIterator.hasNext()) {
				rowNumber++;
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				int parameterIndex = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int position = getPosition(list, listHead.get(parameterIndex));
					// if (position == -1) {
					// conn.rollback();
					// entityError = buildI16dError(rowNumber, "La columna " + listHead.get(parameterIndex) + " no
					// existe en la tabala " + tableName + "$" + xlsSheet,
					// listHead.get(parameterIndex), nameFile);
					// listError.add(entityError);
					// }
					if ("NUMBER".equals(list.get(position).getType())) {
						try {
							sql_statement.setDouble(position + 5, cell.getNumericCellValue());
						} catch (Exception e) {
							conn.rollback();
							entityError = buildI16dError(rowNumber, e.getMessage() + "$" + xlsSheet, listHead.get(parameterIndex), nameFile);
							listError.add(entityError);
						}
					} else if ("VARCHAR2".equals(list.get(position).getType())) {
						try {
							sql_statement.setString(position + 5, cell.getStringCellValue());
						} catch (Exception e) {
							conn.rollback();
							entityError = buildI16dError(rowNumber, e.getMessage() + "$" + xlsSheet, listHead.get(parameterIndex), nameFile);
							listError.add(entityError);
						}
					} else if ("DATE".equals(list.get(position).getType())) {
						try {
							/// use format [PENDIENTE]
							Date d = cell.getDateCellValue();
							sql_statement.setDate(position + 5, new java.sql.Date(d.getTime()));
						} catch (Exception e) {
							conn.rollback();
							entityError = buildI16dError(rowNumber, e.getMessage() + "$" + xlsSheet, listHead.get(parameterIndex), nameFile);
							listError.add(entityError);
						}
					} else {
						conn.rollback();
						entityError = buildI16dError(rowNumber, "Tipo de dato no definido$" + xlsSheet, listHead.get(parameterIndex), nameFile);
						listError.add(entityError);
					}
					parameterIndex++;
				}
				Long nextVal = sqlNextValue(tableName);
				sql_statement.setLong(1, nextVal);
				sql_statement.setLong(2, contentId);
				sql_statement.setString(3, null);
				sql_statement.setString(4, null);
				try {
					sql_statement.executeUpdate();
					if (nameProcedure != null) {
						ValidatorProcedure valPro = callValProcedure(nameProcedure, tableName, nextVal);
						sqlUpdateData(tableName, nextVal, valPro);
					}
				} catch (SQLException e) {
					conn.rollback();
					entityError = buildI16dError(rowNumber, e.getMessage() + ". Revisar las columnas de la tabla " + tableName + "$" + xlsSheet, "n/a", nameFile);
					listError.add(entityError);
				}
			}
			input_document.close();
			sql_statement.close();
		} catch (SQLException | IOException | InvalidFormatException | EncryptedDocumentException ex) {
			Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (sql_statement != null) {
				try {
					sql_statement.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			if (my_xls_workbook != null) {
				try {
					my_xls_workbook.close();
				} catch (IOException ex) {
					Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return listError;
	}

	public static List<I16dError> proccessCSV(String csvComma, String csvHead, String csvSplit, String tableName, List<I16dArchiveMapper> list,
			byte[] file, String nameFile, Long contentId, String nameProcedure, List<I16dError> listError) {
		PreparedStatement sql_statement = null;
		try {
			I16dError entityError;
			String jdbc_insert_sql = sqlInsert(tableName, list);
			sql_statement = conn.prepareStatement(jdbc_insert_sql);
			InputStreamReader inputStream = new InputStreamReader(new ByteArrayInputStream(file));
			CSVReader reader = new CSVReader(inputStream, csvSplit.charAt(0), csvComma.charAt(0));
			String[] headerRow;
			int rowNumber = 0;
			List<String> listHead = new ArrayList<>();
			if ("SI".equals(csvHead)) {
				headerRow = reader.readNext();
				rowNumber++;
				if (null == headerRow) {
					throw new FileNotFoundException("No columns defined in given CSV file. Please check the CSV file format.");
				} else {
					for (String valueHead : headerRow) {
						if (getPosition(list, valueHead) == -1) {
							entityError = buildI16dError(rowNumber, "La columna [" + valueHead + "] no existe en la tabla [" + tableName + "]", "n/a", nameFile);
							listError.add(entityError);
							return listError;
						}
						listHead.add(valueHead);
					}
					if (list.size() != listHead.size()) {
						entityError = buildI16dError(rowNumber, "El Archivo y la Tabla [" + tableName + "] no tienen la misma cantidad de columnas", "n/a", nameFile);
						listError.add(entityError);
						return listError;
					}
				}
			}
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				rowNumber++;
				if (null != nextLine) {
					int parameterIndex = 0;
					for (String valueCol : nextLine) {
						int position = getPosition(list, listHead.get(parameterIndex));
						// if (position == -1) {
						// conn.rollback();
						// entityError = buildI16dError(rowNumber, "La columna " + listHead.get(parameterIndex) + " no
						// existe en la tabla " + tableName,
						// listHead.get(parameterIndex), nameFile);
						// listError.add(entityError);
						// }
						if ("NUMBER".equals(list.get(position).getType())) {
							try {
								sql_statement.setDouble(position + 5, Integer.parseInt(valueCol));
							} catch (Exception e) {
								conn.rollback();
								entityError = buildI16dError(rowNumber, e.getMessage(), listHead.get(parameterIndex), nameFile);
								listError.add(entityError);
							}
						} else if ("VARCHAR2".equals(list.get(position).getType())) {
							try {
								sql_statement.setString(position + 5, valueCol);
							} catch (Exception e) {
								conn.rollback();
								entityError = buildI16dError(rowNumber, e.getMessage(), listHead.get(parameterIndex), nameFile);
								listError.add(entityError);
							}
						} else if ("DATE".equals(list.get(position).getType())) {
							try {
								Date date = HelpLocal.convertToDate(valueCol, list.get(position).getFormat());
								sql_statement.setDate(position + 5, new java.sql.Date(date.getTime()));
							} catch (Exception e) {
								conn.rollback();
								entityError = buildI16dError(rowNumber, e.getMessage(), listHead.get(parameterIndex), nameFile);
								listError.add(entityError);
							}
						} else {
							conn.rollback();
							entityError = buildI16dError(rowNumber, "Tipo de dato no definido", listHead.get(parameterIndex), nameFile);
							listError.add(entityError);
						}
						parameterIndex++;
					}
					Long nextVal = sqlNextValue(tableName);
					sql_statement.setLong(1, nextVal);
					sql_statement.setLong(2, contentId);
					sql_statement.setString(3, null);
					sql_statement.setString(4, null);
					try {
						sql_statement.executeUpdate();
						if (nameProcedure != null) {
							ValidatorProcedure valPro = callValProcedure(nameProcedure, tableName, nextVal);
							sqlUpdateData(tableName, nextVal, valPro);
						}
					} catch (SQLException e) {
						conn.rollback();
						entityError = buildI16dError(rowNumber, e.getMessage() + ". Revisar las columnas de la tabla " + tableName, "n/a", nameFile);
						listError.add(entityError);
					}
				}
			}
		} catch (SQLException | IOException ex) {
			Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (sql_statement != null) {
				try {
					sql_statement.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProccessData.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return listError;
	}

	public static String sqlInsert(String tableName, List<I16dArchiveMapper> list) {
		String sql = "INSERT INTO " + tableName + " (ID_BASE, CONTENT_ID, VAL_STATE, VAL_DESCRIPTION,";
		String value = " VALUES (?,?,?,?,";
		for (I16dArchiveMapper am : list) {
			sql = sql + am.getColTable() + ",";
			value = value + "?,";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		value = value.substring(0, value.length() - 1) + ")";
		sql = sql + value;
		return sql;
	}

	public static int getPosition(List<I16dArchiveMapper> list, String colArchive) {
		for (int i = 0; i < list.size(); i++) {
			if (colArchive.toUpperCase().equals(list.get(i).getColArchive())) {
				return i;
			}
		}
		return -1;
	}

	public static I16dError buildI16dError(int atLine, String description, String nameColumn, String nomArchive) {
		I16dError entity = new I16dError();
		entity.setAtLine(atLine);
		entity.setDescription(description);
		entity.setNameColumn(nameColumn);
		entity.setNomArchive(nomArchive);
		return entity;
	}
}
