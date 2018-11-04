/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yracnet.spec;

import net.yracnet.data.SourceEntry;

/**
 *
 * @author yracnet
 */
public interface Generator {

    void start(Context ctx, SourceEntry src);
}
