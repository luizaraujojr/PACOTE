library("ggplot2")

library("stringr");
library("reshape2");

data1 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/5metricas20variacoes-20kavals-20instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
data1new <-cbind (data1, "Cenário" = "Combinação variada das 5 métricas")


data2 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/5metricas-20kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);

data2new <-cbind (data2, "metricconfig" = "metricconfig")
data2new1 <-cbind (data2new, "Cenário" = "Combinação padrão das 5 métricas")

data <- rbind(data1new, data2new1)
	
data$instance <- factor(data$instance,      # Reordering group factor levels
                         levels = c("jstl (18)", "jnanoxml (25)", "seemp (31)", "apache-zip (36)", "notepad (46)", "udt-java (56)", "javaocr (59)", "servlet-api (63)", "forms (68)", "jscatterplot (74)", "jfluid (82)","junit (100)" ,"xmldom (119)" , "tinytim (134)","javacc (154)","java-geom (172)", "xmlapi (184)", "jmetal (190)", "dom4j (195)", "pdf-renderer (199)"))	
	
#pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-metric-fixed-variable20kaval-5metricas.pdf", sep = ""), width=8,height=8)

ggplot(data, aes(factor(instance), mojo)) +
     geom_boxplot(aes(fill = Cenário)) +
	 geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
	 xlab("Número de Classes") + 
	 ylab("MoJoFM") + 
     theme(legend.position="bottom", axis.text.x = element_text(angle = 90, hjust=1))
	
	
	 	dev.off();




nClasses <- sort(unique(data$nclasses));

columns <- c("nClasses","mojofixa", "mojovariavel", "pvalue")
	
result <- matrix(nrow = 0, ncol=length(columns))  
colnames(result) <- columns  
	for (nClasses_ in nClasses)
		{
		pData <- subset(data, Cenário == "Combinação padrão das 5 métricas" & nclasses == nClasses_ );
		vData <- subset(data, Cenário == "Combinação variada das 5 métricas" & nclasses == nClasses_ );
	
		result <- rbind(result, c(
		nClasses_,
		mean(pData$mojo),
		mean(vData$mojo),
		wilcox.test(pData$mojo,vData$mojo)$p.value		
		))
	}

