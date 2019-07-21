package com.github.jhejderup;

import com.github.jhejderup.data.ModuleClasspath;
import com.github.jhejderup.data.type.MavenResolvedCoordinate;
import com.github.jhejderup.generator.WalaCallgraphConstructor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.HashSet;

public class GenerateCallGraph {

    private static Logger logger = LoggerFactory.getLogger(GenerateCallGraph.class);

    public static void main(String[] args) {
        
        var pomXML = args[0];
      //  System.out.println(pomXML.replace("pom.xml","target/classes"));
      //  System.out.println("");
        //2. Resolve dependencies of pom.xml files
        try {
            var depz = Maven.resolver()
                    .loadPomFromFile(pomXML)
                    .importCompileAndRuntimeDependencies()
                    .resolve()
                    .withTransitivity().asFile();
            
            var packages = new HashSet<String>(); 
            int i;
            for (i = 0; i < depz.length; i++){
                try (ZipFile archive = new ZipFile(depz[i])) {
                      // sort entries by name to always create folders first
                List<? extends ZipEntry> entries = archive.stream()
                                                      .sorted(Comparator.comparing(ZipEntry::getName))
                                                      .collect(Collectors.toList());
                // copy each entry in the dest path
                for (ZipEntry entry : entries) {
                    try {
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        var path = Paths.get(entry.getName());    
                        var dir = path.getParent().toString();
                        var size = dir.split("/");
                        if(size.length > 2){
                            packages.add(size[0] + "." +size[1] + "."+ size[2] + ".*");
                        } else if (size.length > 1) {
                          packages.add(size[0] + "." +size[1] + ".*");
                        } else {
                         packages.add(size[0] + ".*");    
                        }
                        
                    }
                        } catch(Exception e) {
                            continue;
                        }
                 }
                }
                  
            }
            
            System.out.print(String.join(",", packages));

        } catch (Exception e) {
            System.out.print("");
//             logger.error("Failed for {} with exception: {}", pomXML, e);
             e.printStackTrace();
        }
    }


}
