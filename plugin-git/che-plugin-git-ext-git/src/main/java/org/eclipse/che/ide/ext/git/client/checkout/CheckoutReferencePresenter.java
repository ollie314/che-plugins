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
package org.eclipse.che.ide.ext.git.client.checkout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import org.eclipse.che.api.git.gwt.client.GitServiceClient;
import org.eclipse.che.api.git.shared.BranchCheckoutRequest;
import org.eclipse.che.api.project.shared.dto.ProjectDescriptor;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.event.OpenProjectEvent;
import org.eclipse.che.ide.api.notification.NotificationManager;
import org.eclipse.che.ide.dto.DtoFactory;
import org.eclipse.che.ide.ext.git.client.GitLocalizationConstant;
import org.eclipse.che.ide.ext.git.client.GitOutputPartPresenter;
import org.eclipse.che.ide.rest.AsyncRequestCallback;

/**
 * Presenter for checkout reference(branch, tag) name or commit hash.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class CheckoutReferencePresenter implements CheckoutReferenceView.ActionDelegate {
    private final NotificationManager     notificationManager;
    private final GitOutputPartPresenter  console;
    private final DtoFactory              dtoFactory;
    private final GitServiceClient        service;
    private final AppContext              appContext;
    private final EventBus                eventBus;
    private final GitLocalizationConstant constant;
    private final CheckoutReferenceView   view;

    @Inject
    public CheckoutReferencePresenter(CheckoutReferenceView view,
                                      GitServiceClient service,
                                      EventBus eventBus,
                                      AppContext appContext,
                                      GitLocalizationConstant constant,
                                      GitOutputPartPresenter console,
                                      NotificationManager notificationManager,
                                      DtoFactory dtoFactory) {
        this.view = view;
        this.console = console;
        this.dtoFactory = dtoFactory;
        this.view.setDelegate(this);
        this.service = service;
        this.appContext = appContext;
        this.constant = constant;
        this.eventBus = eventBus;
        this.notificationManager = notificationManager;
    }

    /** Show dialog. */
    public void showDialog() {
        view.setCheckoutButEnableState(false);
        view.showDialog();
    }

    @Override
    public void onCancelClicked() {
        view.close();
    }

    @Override
    public void onCheckoutClicked(final String reference) {
        view.close();
        final ProjectDescriptor project = appContext.getCurrentProject().getRootProject();
        service.branchCheckout(project,
                               dtoFactory.createDto(BranchCheckoutRequest.class)
                                         .withName(reference)
                                         .withCreateNew(false),
                               new AsyncRequestCallback<String>() {
                                   @Override
                                   protected void onSuccess(String result) {
                                       String projectPath = project.getPath();
                                       //In this case we can have unconfigured state of the project,
                                       //so we must repeat the logic which is performed when we open a project
                                       eventBus.fireEvent(new OpenProjectEvent(projectPath));
                                   }

                                   @Override
                                   protected void onFailure(Throwable exception) {
                                       final String errorMessage = (exception.getMessage() != null)
                                                                   ? exception.getMessage()
                                                                   : constant.checkoutFailed(reference);
                                       console.printError(errorMessage);
                                       notificationManager.showError(errorMessage);
                                   }
                               }
                              );
    }

    @Override
    public void referenceValueChanged(String reference) {
        view.setCheckoutButEnableState(isInputCorrect(reference));
    }

    @Override
    public void onEnterClicked() {
        String reference = view.getReference();
        if (isInputCorrect(reference)) {
            onCheckoutClicked(reference);
        }
    }

    private boolean isInputCorrect(String reference) {
        return reference != null && !reference.isEmpty();
    }
}