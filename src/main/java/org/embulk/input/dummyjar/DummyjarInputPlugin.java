package org.embulk.input.dummyjar;

import java.util.List;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskReport;
import org.embulk.config.TaskSource;
import org.embulk.spi.Exec;
import org.embulk.spi.InputPlugin;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.PageOutput;
import org.embulk.spi.Schema;
import org.embulk.spi.type.Types;

public class DummyjarInputPlugin
        implements InputPlugin
{
    public interface PluginTask
            extends Task
    {
    }

    @Override
    public ConfigDiff transaction(ConfigSource config,
            InputPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);

        Schema schema = Schema.builder()
            .add("id", Types.LONG)
            .add("name", Types.STRING)
            .build();
        int taskCount = 1;  // number of run() method calls

        return resume(task.dump(), schema, taskCount, control);
    }

    @Override
    public ConfigDiff resume(TaskSource taskSource,
            Schema schema, int taskCount,
            InputPlugin.Control control)
    {
        control.run(taskSource, schema, taskCount);
        return Exec.newConfigDiff();
    }

    @Override
    public void cleanup(TaskSource taskSource,
            Schema schema, int taskCount,
            List<TaskReport> successTaskReports)
    {
    }

    @Override
    public TaskReport run(TaskSource taskSource,
            Schema schema, int taskIndex,
            PageOutput output)
    {
        PluginTask task = taskSource.loadTask(PluginTask.class);

        try (PageBuilder pageBuilder = new PageBuilder(Exec.getBufferAllocator(), schema, output)) {
            pageBuilder.setLong(0, 42);
            pageBuilder.setString(1, "foo");
            pageBuilder.addRecord();
            pageBuilder.setLong(0, 84);
            pageBuilder.setString(1, "bar");
            pageBuilder.addRecord();
            pageBuilder.finish();
        }

        return Exec.newTaskReport();
    }

    @Override
    public ConfigDiff guess(ConfigSource config)
    {
        return Exec.newConfigDiff();
    }
}
