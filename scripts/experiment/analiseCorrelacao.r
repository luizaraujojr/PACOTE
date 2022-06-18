dataInstances <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/valores5MetricasInstancias.csv", sep=";", header=TRUE);

# [1] "jedit-5.5.0.odem"       "jhotdraw-7.6.0.odem"    "junit-4.12.3.odem"     
# [4] "projectName"            "apache_zip36C.odem"     "dom4j-1.5.2195C.odem"  
# [7] "forms-1.3.068C.odem"    "javacc154C.odem"        "JavaGeom172C.odem"     
#[10] "javaocr59C.odem"        "jfluid-1.7.082C.odem"   "JMetal190C.odem"       
#[13] "jnanoxml25C.odem"       "jscatterplot74C.odem"   "jstl-1.0.618C.odem"    
#[16] "junit-3.8.1100C.odem"   "notepad-model46C.odem"  "pdf_renderer199C.odem" 
#[19] "seemp31C.odem"          "servletapi-2.363C.odem" "tinytim134C.odem"      
#[22] "udt-java56C.odem"       "xmlapi184C.odem"        "xmldom119C.odem"  

data <- subset(dataInstances, projectName=="JavaGeom172C.odem");
data <- subset(data, select =-projectName)

data$intDep = as.numeric(data$intDep)
data$extDep = as.numeric(data$extDep)
data$intDepClas = as.numeric(data$intDepClas)
data$extDepClas = as.numeric(data$extDepClas)
data$extclasDep = as.numeric(data$extclasDep)

cor(data)

library(psych)

pairs.panels(data)