/*******************************************************************************
 * Copyright (c) 2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.plugin.angularjs.core.client;

import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.plugin.angularjs.core.client.editor.AngularJSResources;
import org.eclipse.che.plugin.angularjs.core.client.share.Const;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Florent Benoit
 */
@Singleton
@Extension(title = "GruntJS")

public class GruntJsExtension extends JsExtension {

    @Inject
    public GruntJsExtension(IconRegistry iconRegistry,
                            AngularJSResources resources) {
        super(Const.GRUNT_JS_ID, iconRegistry, resources);
    }
}
