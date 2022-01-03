package com.codescan.admin.modules.codescan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codescan.admin.common.exception.ApiException;
import com.codescan.admin.modules.codescan.mapper.SourceCodeMapper;
import com.codescan.admin.modules.codescan.model.SourceCodeVo;
import com.codescan.admin.modules.codescan.model.SourceReportVo;
import com.codescan.admin.modules.codescan.service.ISourceCodeService;
import com.codescan.admin.modules.codescan.service.ISourceReportService;
import com.codescan.admin.modules.sys.model.SysFile;
import com.codescan.admin.modules.sys.service.IFileService;
import com.codescan.admin.utils.ZipUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Service
public class SourceCodeServiceImpl extends ServiceImpl<SourceCodeMapper, SourceCodeVo> implements ISourceCodeService {
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISourceReportService sourceReportService;
    @Autowired
    private ZipUtils zipUtils;


    @Override
    public boolean saveSourceCode(SourceCodeVo sourceCodeVo) {
        sourceCodeVo.setUploadTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        boolean flag = this.save(sourceCodeVo);
        String path = sourceCodeVo.getCodePath();
        if(path.toLowerCase().endsWith(".zip")){
            SysFile sysFile = fileService.findByMinioUrl(path);
            if(Objects.isNull(sysFile)){
                throw new ApiException("获取文件信息失败!");
            }
            new Thread(()->analysisSourceCode(sourceCodeVo,sysFile)).start();
        }else if(path.toLowerCase().endsWith(".py")){
            //TODO
        }

        return flag;
    }

    @SneakyThrows
    public void analysisSourceCode(SourceCodeVo sourceCodeVo, SysFile sysFile){
        String localPath = sysFile.getLocalUrl();
        if(StringUtils.isEmpty(localPath)){
            throw new ApiException("获取文件信息失败!");
        }
        String location = zipUtils.unzipFile(localPath);
        this.execPython(location,sourceCodeVo);
    }

    private void execPython(String pythonFilePath,SourceCodeVo sourceCodeVo){
        String pythonFiles = pythonFilePath + File.separator + "*.py";
        String reportType = StringUtils.isEmpty(sourceCodeVo.getReportType())?"html":sourceCodeVo.getReportType();
        try {
            //'csv', 'custom', 'html', 'json', 'screen', 'txt', 'xml', 'yaml'
            String execCommand = "";
            if("csv".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("custom".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("json".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("screen".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("txt".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("xml".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else if("yaml".equalsIgnoreCase(reportType)){
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }else {
                execCommand = "bandit " + pythonFiles + "-f html -o " + pythonFilePath+File.separator+"report.html";
            }

            String execCommand2 = "bandit " + pythonFiles ;
            String desc = "";
            Process process = Runtime.getRuntime().exec(execCommand);
            Process process2 = Runtime.getRuntime().exec(execCommand2);
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            String line = null;
            while((line = reader2.readLine()) != null){
                log.info(line);
                desc += line;
            }
            reader2.close();
            process.waitFor();
            process2.waitFor();
            String localPath = pythonFilePath + File.separator + "report."+reportType;
            String url = fileService.uploadFile(localPath);
            SourceReportVo reportVo = SourceReportVo.builder()
                    .desc(desc)
                    .filePath(url)
                    .reportName(sourceCodeVo.getName())
                    .sourceId(sourceCodeVo.getId())
                    .build();
            sourceReportService.save(reportVo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
