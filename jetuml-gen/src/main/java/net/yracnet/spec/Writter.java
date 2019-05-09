/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.spec;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import java.io.File;

/**
 *
 * @author wyujra
 */
public interface Writter {

	public void writeJet(File file, Diagram diagram);

	public void writeImage(File file, Diagram diagram);

	public void writeResume();

}
