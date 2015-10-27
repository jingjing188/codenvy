/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 *  [2012] - [2015] Codenvy, S.A.
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.im.cli.command;

import com.codenvy.im.artifacts.ArtifactFactory;
import com.codenvy.im.artifacts.CDECArtifact;
import com.codenvy.im.artifacts.VersionLabel;
import com.codenvy.im.response.AvailableVersionInfo;
import com.codenvy.im.response.InstallArtifactInfo;
import com.codenvy.im.response.UpdatesArtifactInfo;
import com.codenvy.im.response.VersionArtifactInfo;

import org.apache.karaf.shell.commands.Command;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.commons.json.JsonParseException;

import java.io.IOException;
import java.util.List;

import static com.codenvy.im.utils.Commons.toJson;

/**
 * @author Alexander Reshetnyak
 */
@Command(scope = "codenvy", name = "im-version", description = "Print the list of available latest versions and installed ones")
public class VersionCommand extends AbstractIMCommand {
    private static final String LATEST_STABLE_MESSAGE = "You are running the latest stable version of Codenvy!";
    private static final String NEW_VERSION_MESSAGE   = "There is a new stable version of Codenvy available. Run im-download %s.";

    @Override
    protected void doExecuteCommand() throws Exception {
        VersionArtifactInfo info = new VersionArtifactInfo();

        info.setArtifact(CDECArtifact.NAME);
        InstallArtifactInfo installedCDEC = getInstalledCDECArtifactInfo();
        if (installedCDEC != null) {
            info.setVersion(installedCDEC.getVersion());
            info.setLabel(installedCDEC.getLabel());
        }

        List<UpdatesArtifactInfo> updatesCodenvy = facade.getAllUpdates(ArtifactFactory.createArtifact(CDECArtifact.NAME));

        AvailableVersionInfo availableVersion = new AvailableVersionInfo();
        availableVersion.setStable(getLatestStableVersion(updatesCodenvy));
        availableVersion.setUnstable(getLatestUnstableVersion(updatesCodenvy));

        if (availableVersion.getStable() != null || availableVersion.getUnstable() != null) {
            info.setAvailableVersion(availableVersion);
        }

        if (info.getVersion() != null) {
            if ((info.getAvailableVersion() == null || info.getAvailableVersion().getStable() == null)) {
                info.setStatus(LATEST_STABLE_MESSAGE);
            } else if (info.getAvailableVersion() != null && info.getAvailableVersion().getStable() != null) {
                if (info.getVersion().equals(info.getAvailableVersion().getStable())) {
                    info.setStatus(LATEST_STABLE_MESSAGE);
                } else {
                    info.setStatus(String.format(NEW_VERSION_MESSAGE, info.getAvailableVersion().getStable()));
                }
            }
        }

        console.println(toJson(info));
    }

    @Nullable
    private InstallArtifactInfo getInstalledCDECArtifactInfo() throws IOException {
        for (InstallArtifactInfo info : facade.getInstalledVersions()) {
            if (info.getArtifact().equals(CDECArtifact.NAME)) {
                return info;
            }
        }
        return null;
    }

    @Nullable
    private String getLatestStableVersion(List<UpdatesArtifactInfo> updatesCodenvy) throws IOException, JsonParseException {
        for (int i = updatesCodenvy.size() - 1; i >= 0; i--) {
            UpdatesArtifactInfo updateInfo = updatesCodenvy.get(i);

            if (updateInfo.getLabel() == VersionLabel.STABLE) {
                return updateInfo.getVersion();
            }
        }

        return null;
    }

    @Nullable
    private String getLatestUnstableVersion(List<UpdatesArtifactInfo> updatesCodenvy) {
        for (int i = updatesCodenvy.size() - 1; i>=0; i--) {
            UpdatesArtifactInfo updateInfo = updatesCodenvy.get(i);

            if (updateInfo.getLabel() == VersionLabel.UNSTABLE) {
                return updateInfo.getVersion();
            }
        }

        return null;
    }
}
