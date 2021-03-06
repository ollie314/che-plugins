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
package org.eclipse.che.plugin.docker.client;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.che.commons.json.JsonHelper;
import org.eclipse.che.dto.server.DtoFactory;
import org.eclipse.che.inject.ConfigurationProperties;
import org.eclipse.che.plugin.docker.client.dto.AuthConfig;
import org.eclipse.che.plugin.docker.client.dto.AuthConfigs;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

import static org.eclipse.che.commons.lang.Strings.isNullOrEmpty;

/**
 * Collects auth configurations for private docker registries. Credential might be configured in .properties files, see details {@link
 * org.eclipse.che.inject.CodenvyBootstrap}. Credentials configured as (key=value) pairs. Key is string that starts with prefix
 * {@code docker.registry.auth.} followed by url and credentials of docker registry server.
 * <pre>{@code
 * docker.registry.auth.url=localhost:5000
 * docker.registry.auth.username=user1
 * docker.registry.auth.password=pass
 * docker.registry.auth.email=user1@email.com
 * }</pre>
 *
 * @author Alexander Garagatyi
 */
@Singleton
public class InitialAuthConfig {
    private static final String CONFIGURATION_PREFIX         = "docker.registry.auth.";
    private static final String CONFIGURATION_PREFIX_PATTERN = "docker\\.registry\\.auth\\..+";

    AuthConfig predefinedConfig;

    @Inject
    public InitialAuthConfig(ConfigurationProperties configurationProperties) {
        String serverAddress = "https://index.docker.io/v1/";
        String username = null, password = null, email = null;
        for (Map.Entry<String, String> e : configurationProperties.getProperties(CONFIGURATION_PREFIX_PATTERN).entrySet()) {
            final String classifier = e.getKey().replaceFirst(CONFIGURATION_PREFIX, "");
            switch (classifier) {
                case "url": {
                    serverAddress = e.getValue();
                    break;
                }
                case "email": {
                    email = e.getValue();
                    break;
                }
                case "username": {
                    username = e.getValue();
                    break;
                }
                case "password": {
                    password = e.getValue();
                    break;
                }
            }
        }
        if (!isNullOrEmpty(serverAddress) && !isNullOrEmpty(username) && !isNullOrEmpty(password) && !isNullOrEmpty(email)) {
            predefinedConfig = DtoFactory.newDto(AuthConfig.class).withServeraddress(serverAddress)
                                         .withUsername(username)
                                         .withPassword(password)
                                         .withEmail(email);
        }
    }


    public String getAuthConfigHeader() {
        if (predefinedConfig != null) {
            return Base64.encodeBase64String(JsonHelper.toJson(predefinedConfig).getBytes());
        } else {
            return "";
        }
    }

    public AuthConfig getInitialAuthConfig() {
        if (predefinedConfig != null) {
            return predefinedConfig;
        } else {
            return null;
        }
    }

    public AuthConfigs getAuthConfigs() {
        AuthConfigs authConfigs = DtoFactory.newDto(AuthConfigs.class);
        if (predefinedConfig != null) {
            authConfigs.getConfigs().put(predefinedConfig.getServeraddress(), predefinedConfig);
        }
        return authConfigs;
    }
}
