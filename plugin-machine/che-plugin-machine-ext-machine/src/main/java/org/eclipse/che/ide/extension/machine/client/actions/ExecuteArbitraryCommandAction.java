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
package org.eclipse.che.ide.extension.machine.client.actions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.analytics.client.logger.AnalyticsEventLogger;
import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.extension.machine.client.MachineLocalizationConstant;
import org.eclipse.che.ide.extension.machine.client.command.execute.ExecuteArbitraryCommandPresenter;
import org.eclipse.che.ide.extension.machine.client.machine.MachineManager;
import org.eclipse.che.ide.ui.dialogs.DialogFactory;

/**
 * Action to execute arbitrary command in machine.
 *
 * @author Artem Zatsarynnyy
 */
@Singleton
public class ExecuteArbitraryCommandAction extends Action {
    private final MachineManager                   machineManager;
    private final DialogFactory                    dialogFactory;
    private final MachineLocalizationConstant      localizationConstant;
    private final AppContext                       appContext;
    private final ExecuteArbitraryCommandPresenter presenter;
    private final AnalyticsEventLogger             eventLogger;

    @Inject
    public ExecuteArbitraryCommandAction(ExecuteArbitraryCommandPresenter presenter,
                                         MachineManager machineManager,
                                         DialogFactory dialogFactory,
                                         MachineLocalizationConstant localizationConstant,
                                         AppContext appContext,
                                         AnalyticsEventLogger eventLogger) {
        super(localizationConstant.executeArbitraryCommandControlTitle(),
              localizationConstant.executeArbitraryCommandControlDescription(),
              null);
        this.presenter = presenter;
        this.machineManager = machineManager;
        this.dialogFactory = dialogFactory;
        this.localizationConstant = localizationConstant;
        this.appContext = appContext;
        this.eventLogger = eventLogger;
    }

    /** {@inheritDoc} */
    @Override
    public void update(ActionEvent e) {
        e.getPresentation().setVisible(appContext.getCurrentProject() != null);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        eventLogger.log(this);

        if (machineManager.getCurrentMachineId() != null) {
            presenter.show();
        } else {
            dialogFactory.createMessageDialog("", localizationConstant.noCurrentMachine(), null).show();
        }
    }
}