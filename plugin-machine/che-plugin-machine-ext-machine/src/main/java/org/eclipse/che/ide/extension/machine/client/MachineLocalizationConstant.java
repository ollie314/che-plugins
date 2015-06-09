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
package org.eclipse.che.ide.extension.machine.client;

import com.google.gwt.i18n.client.Messages;

/**
 * I18n constants for the Machine extension.
 *
 * @author Artem Zatsarynnyy
 */
public interface MachineLocalizationConstant extends Messages {

    /* Buttons */
    @Key("button.execute")
    String executeButton();

    @Key("button.apply")
    String applyButton();

    @Key("button.ok")
    String okButton();

    @Key("button.cancel")
    String cancelButton();

    @Key("button.close")
    String closeButton();


    /* Actions */
    @Key("mainMenu.run.name")
    String mainMenuRunName();

    @Key("control.executeArbitraryCommand.text")
    String executeArbitraryCommandControlTitle();

    @Key("control.executeArbitraryCommand.description")
    String executeArbitraryCommandControlDescription();

    @Key("control.selectCommand.text")
    String selectCommandControlTitle();

    @Key("control.selectCommand.description")
    String selectCommandControlDescription();

    @Key("control.runSelectedCommand.text")
    String executeSelectedCommandControlTitle();

    @Key("control.runSelectedCommand.description")
    String executeSelectedCommandControlDescription();

    @Key("control.editConfigurations.text")
    String editConfigurationsControlTitle();

    @Key("control.editConfigurations.description")
    String editConfigurationsControlDescription();

    @Key("control.terminateMachine.text")
    String terminateMachineControlTitle();

    @Key("control.terminateMachine.description")
    String terminateMachineControlDescription();

    @Key("control.clearMachineConsole.text")
    String clearConsoleControlTitle();

    @Key("control.clearMachineConsole.description")
    String clearConsoleControlDescription();


    /* Messages */
    @Key("messages.noCurrentMachine")
    String noCurrentMachine();


    /* MachineStateNotifier */
    @Key("notification.creatingMachine")
    String notificationCreatingMachine(String machineId);

    @Key("notification.machineIsRunning")
    String notificationMachineIsRunning(String machineId);

    @Key("notification.destroyingMachine")
    String notificationDestroyingMachine(String machineId);

    @Key("notification.machineDestroyed")
    String notificationMachineDestroyed(String machineId);


    /* MachineConsoleView */
    @Key("view.machineConsole.title")
    String machineConsoleViewTitle();

    @Key("view.machineConsole.tooltip")
    String machineConsoleViewTooltip();


    /* OutputsContainerView */
    @Key("view.outputsConsole.title")
    String outputsConsoleViewTitle();

    @Key("view.outputsConsole.tooltip")
    String outputsConsoleViewTooltip();


    /* ExecuteArbitraryCommandView */
    @Key("view.executeCommand.title")
    String executeCommandViewTitle();


    /* EditConfigurationsView */
    @Key("view.editConfigurations.title")
    String editConfigurationsViewTitle();

    @Key("view.editConfigurations.hint")
    String editConfigurationsViewHint();

    @Key("view.editConfigurations.name.text")
    String editConfigurationsViewNameText();

    @Key("view.editConfigurations.add.text")
    String editConfigurationsViewAddText();

    @Key("view.editConfigurations.add.title")
    String editConfigurationsViewAddTitle();

    @Key("view.editConfigurations.remove.text")
    String editConfigurationsViewRemoveText();

    @Key("view.editConfigurations.remove.title")
    String editConfigurationsViewRemoveTitle();

    @Key("view.editConfigurations.remove.confirmation")
    String editConfigurationsRemoveConfirmation(String commandName);

    @Key("view.editConfigurations.saveChanges.title")
    String editConfigurationsSaveChangesTitle();

    @Key("view.editConfigurations.saveChanges.text")
    String editConfigurationsSaveChangesConfirmation(String commandName);

    @Key("view.editConfigurations.saveChanges.save")
    String editConfigurationsSaveChangesSave();

    @Key("view.editConfigurations.saveChanges.discard")
    String editConfigurationsSaveChangesDiscard();


    @Key("process.active")
    String processActive();

    @Key("process.stopped")
    String processStopped();

    @Key("process.table.name")
    String processTableName();

    @Key("process.table.protocol")
    String processTableProtocol();

    @Key("process.table.port")
    String processTablePort();

    @Key("process.table.time")
    String processTableTime();

    @Key("process.table.active")
    String processTableActive();

    @Key("tab.processes")
    String tabProcesses();

    @Key("tab.terminal")
    String tabTerminal();

    @Key("machine.perspective.action.description")
    String perspectiveActionDescription();

    @Key("machine.perspective.action.tooltip")
    String perspectiveActionTooltip();
}