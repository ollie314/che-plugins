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
package org.eclipse.che.plugin.angularjs.core.server.project.type.grunt;

import org.eclipse.che.plugin.angularjs.core.shared.ProjectAttributes;

import org.eclipse.che.api.project.server.type.ProjectType;
import org.eclipse.che.ide.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;


/**
 * @author Vitaliy Parfonov
 * @author Dmitry Shnurenko
 */
@Singleton
public class GruntJSProjectType extends ProjectType {

    @Inject
    public GruntJSProjectType(GruntValueProviderFactory gruntValueProviderFactory) {
        super(ProjectAttributes.GRUNT_ID, ProjectAttributes.GRUNT_NAME, true, false);
        addConstantDefinition(Constants.LANGUAGE, Constants.LANGUAGE, ProjectAttributes.PROGRAMMING_LANGUAGE);
        addConstantDefinition(Constants.FRAMEWORK, Constants.FRAMEWORK, ProjectAttributes.BASIC_JS_FRAMEWORK);
        addVariableDefinition(ProjectAttributes.HAS_CONFIG_FILE, "project has config file", false, gruntValueProviderFactory);
        setDefaultRunner("system:/javascript/web/grunt");
        addRunnerCategories(Arrays.asList(ProjectAttributes.RUNNER_CATEGORY));
    }


}
