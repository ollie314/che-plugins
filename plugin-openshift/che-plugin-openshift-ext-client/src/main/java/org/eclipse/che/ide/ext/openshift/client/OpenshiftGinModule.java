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
package org.eclipse.che.ide.ext.openshift.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import org.eclipse.che.ide.api.extension.ExtensionGinModule;
import org.eclipse.che.ide.ext.openshift.client.project.wizard.CreateProjectWizardFactory;

/**
 * @author Sergii Leschenko
 */
@ExtensionGinModule
public class OpenshiftGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(OpenshiftServiceClient.class).to(OpenshiftServiceClientImpl.class);

        install(new GinFactoryModuleBuilder().build(CreateProjectWizardFactory.class));
    }
}
