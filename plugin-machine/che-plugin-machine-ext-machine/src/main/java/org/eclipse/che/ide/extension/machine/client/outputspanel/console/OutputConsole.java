/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.extension.machine.client.outputspanel.console;

import org.eclipse.che.ide.api.mvp.Presenter;

/**
 * Console for output.
 *
 * @author Artem Zatsarynnyy
 */
public interface OutputConsole extends Presenter {

    /** Return title for the console. */
    String getTitle();

    /** Attach console to the given WebSocket output channel. */
    void attachToOutput(String outputChannel);
}