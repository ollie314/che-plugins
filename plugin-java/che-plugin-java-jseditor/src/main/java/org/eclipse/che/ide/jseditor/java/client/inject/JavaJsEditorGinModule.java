/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.jseditor.java.client.inject;

import org.eclipse.che.ide.api.extension.ExtensionGinModule;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaAnnotationModelFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaCodeAssistProcessorFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaPartitionScanner;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaPartitionerFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaQuickAssistProcessorFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaReconcilerStrategyFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JsJavaEditorConfigurationFactory;
import org.eclipse.che.ide.jseditor.java.client.editor.JavaFormatter;
import org.eclipse.che.ide.jseditor.client.formatter.ContentFormatter;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

@ExtensionGinModule
public class JavaJsEditorGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(JavaCodeAssistProcessorFactory.class));
        install(new GinFactoryModuleBuilder().build(JavaQuickAssistProcessorFactory.class));
        install(new GinFactoryModuleBuilder().build(JsJavaEditorConfigurationFactory.class));
        install(new GinFactoryModuleBuilder().build(JavaReconcilerStrategyFactory.class));
        install(new GinFactoryModuleBuilder().build(JavaAnnotationModelFactory.class));
        bind(ContentFormatter.class).to(JavaFormatter.class);
        bind(JavaPartitionScanner.class);
        bind(JavaPartitionerFactory.class);
    }
}
