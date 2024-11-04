package task;

import task.EndClean.DefaultPropsTypeCleanTask;
import task.EndClean.ExportPageCleanTask;
import task.EndClean.ImportViewCleanTask;
import task.EndClean.PropsTypeCleanTask;
import utils.EditFileUtils;

import java.util.List;

public class EndCleanToExecuteTask {

    public static void endCleanTaskToExecute(String path, List<String> lst) {
        List<String> contents = EditFileUtils.readFile(path);

        if (lst.contains("Clean Import")) {
            ImportViewCleanTask.importViewCleanTask(contents);
        }
        if (lst.contains("PropsType")) {
            PropsTypeCleanTask.propsTypeCleanTask(contents);
        }
        if (lst.contains("DefaultProps")) {
            DefaultPropsTypeCleanTask.defaultPropsTypeCleanTask(contents);
        }
        if (lst.contains("Clean Export")) {
            ExportPageCleanTask.exportPageCleanTask(contents, path);
        }
        EditFileUtils.setContents(path, contents);
    }
}
