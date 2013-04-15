package ru.spbau.launch;

import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import ru.spbau.install.info.specific.JreHomePath;

/**
 * User: yarik
 * Date: 4/15/13
 * Time: 8:48 PM
 */
public class TemplateAlterJreSettingsReplacer implements ProjectComponent {
    Project project;

    public TemplateAlterJreSettingsReplacer(Project project) {
        this.project = project;
    }

    public void initComponent() {
        JreStateProvider jreState = ApplicationManager.getApplication().getComponent(JreStateProvider.class);
        if (jreState.isReady()) {
            replaceWith(project, JreHomePath.getLinuxJrePath(), true);
        }
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "TemplateAlterJreSettingsReplacer";
    }

    public void projectOpened() {
        // called when project is opened
    }

    public void projectClosed() {
        // called when project is being closed
    }


    public static void replaceWith(Project project, String alterJrePath, boolean enabled) {
        RunManagerImpl runManager = (RunManagerImpl)RunManagerImpl.getInstance(project);
        ConfigurationFactory factory = ApplicationConfigurationType.getInstance().getConfigurationFactories()[0];
        ApplicationConfiguration templateApplicationConfig = (ApplicationConfiguration)
                runManager.getConfigurationTemplate(factory).getConfiguration();
        templateApplicationConfig.ALTERNATIVE_JRE_PATH = alterJrePath;
        templateApplicationConfig.ALTERNATIVE_JRE_PATH_ENABLED = enabled;
    }

}
