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
package org.eclipse.che.ide.ext.git.client.delete;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.git.gwt.client.GitServiceClient;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.app.CurrentProject;
import org.eclipse.che.ide.api.event.RefreshProjectTreeEvent;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.GitOutputPartPresenter;
import org.eclipse.che.ide.rest.AsyncRequestCallback;

/**
 * Delete repository command handler, performs deleting Git repository.
 *
 * @author Ann Zhuleva
 */
@Singleton
public class DeleteRepositoryPresenter {
    private final GitOutputPartPresenter  console;

    private GitServiceClient        service;
    private EventBus                eventBus;
    private GitLocalizationConstant constant;
    private AppContext              appContext;
    private NotificationManager     notificationManager;

    /**
     * Create presenter.
     *
     * @param service
     * @param eventBus
     * @param constant
     * @param appContext
     * @param notificationManager
     */
    @Inject
    public DeleteRepositoryPresenter(GitServiceClient service,
                                     EventBus eventBus,
                                     GitLocalizationConstant constant,
                                     GitOutputPartPresenter console,
                                     AppContext appContext,
                                     NotificationManager notificationManager) {
        this.service = service;
        this.eventBus = eventBus;
        this.constant = constant;
        this.console = console;
        this.appContext = appContext;
        this.notificationManager = notificationManager;
    }

    /** Delete Git repository. */
    public void deleteRepository() {
        final CurrentProject project = appContext.getCurrentProject();
        service.deleteRepository(project.getRootProject(), new AsyncRequestCallback<Void>() {
            @Override
            protected void onSuccess(Void result) {
                project.getRootProject().getAttributes().get("vcs.provider.name").clear();

                console.printInfo(constant.deleteGitRepositorySuccess());
                notificationManager.showInfo(constant.deleteGitRepositorySuccess());
                //it's need for hide .git in project tree
                eventBus.fireEvent(new RefreshProjectTreeEvent());
            }

            @Override
            protected void onFailure(Throwable exception) {
                console.printError(exception.getMessage());
                notificationManager.showError(exception.getMessage());
            }
        });
    }
}