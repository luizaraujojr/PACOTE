install.packages("ggplot2")                  # Install & load ggplot2
library("ggplot2")

library("stringr");
library("reshape2");


dataJHotDraw <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/4metricas-20kEvals-JHOTDRAW-050Perturb-DEVref.csv", sep=";", header=TRUE);
	
dataJEdit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/4metricas-20kEvals-JEDIT-050Perturb-DEVref.csv", sep=";", header=TRUE);
	
dataJUnit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/4metricas-20kEvals-JUNIT-050Perturb-DEVref.csv", sep=";", header=TRUE);
	
data <-rbind (dataJHotDraw, dataJEdit, dataJUnit)
data1 <-cbind (data, "software" = str_split( data$cicle, "-", simplify= TRUE )[,1])


data1
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)))

hist(
num <-cbind(
as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,2]),
as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,3]),
as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,4])
)

	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/boxplot-software-ref.pdf", sep = ""), width=8,height=4)

	ggplot(data1, aes(x=software, y=solution)) +
	geom_boxplot() +
	labs(x="Softwares de referência", y = "% de MoJoFM")
  
	dev.off();


	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/histograma-coeficientes.pdf", sep = ""), width=8,height=4)

par(mfrow= c(2,4))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,1]), xlab="Coeficiente A1", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,2]), xlab="Coeficiente A2", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,3]), xlab="Coeficiente A3", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,4]), xlab="Coeficiente A4", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))


hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,5]), xlab="Coeficiente B1", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,6]), xlab="Coeficiente B2", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,7]), xlab="Coeficiente B3", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$nclasses)), ",", simplify= TRUE)[,8]), xlab="Coeficiente B4", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))

	dev.off();


		softwares <- sort(unique(data1$software));
				
		columns <- c("Máximo", "Média", "Mínimo", "Valor MQ");

		result <- matrix(nrow=length(softwares), ncol=length(columns), dimnames=list(softwares, columns));
		
		for (software_ in softwares)
		{
			newdata <- subset(data1, software == software_);
			
			result[software_, "Máximo"] <- max(newdata$solution);
			result[software_, "Média"] <- paste(round(mean(newdata$solution),3),"+-",round(sd(newdata$solution),4));
			result[software_, "Mínimo"] <- min(newdata$solution);
		}
		
result




	ggplot(data, aes(cicle), as.numeric(solution)) +
	
		 geom_boxplot(aes(fill = as.numeric(solution))) + 
		  geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~instance)


	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-2-5kaval-9metricas.pdf", sep = ""), width=8,height=8)
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result2-9metricas-2.5kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data751 <-cbind (data75, "nmetricas" = str_count(data75$solutionreal,",")/2)

	data751$instance <- factor(data751$instance,      # Reordering group factor levels
                         levels = c("jstl (18)", "jnanoxml (25)", "seemp (31)", "apache-zip (36)", "notepad (46)", "udt-java (56)", "javaocr (59)", "servlet-api (63)", "forms (68)", "jscatterplot (74)", "jfluid (82)","junit (100)" ,"xmldom (119)" , "tinytim (134)","javacc (154)","java-geom (172)", "xmlapi (184)", "jmetal (190)", "dom4j (195)", "pdf-renderer (199)"))	
	

		ggplot(data751, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~instance)
		dev.off();
		
		
	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-5kaval-9metricas.pdf", sep = ""), width=8,height=8)
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-5kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data751 <-cbind (data75, "nmetricas" = str_count(data75$solutionreal,",")/2)

	data751$instance <- factor(data751$instance,      # Reordering group factor levels
                         levels = c("jstl (18)", "jnanoxml (25)", "seemp (31)", "apache-zip (36)", "notepad (46)", "udt-java (56)", "javaocr (59)", "servlet-api (63)", "forms (68)", "jscatterplot (74)", "jfluid (82)","junit (100)" ,"xmldom (119)" , "tinytim (134)","javacc (154)","java-geom (172)", "xmlapi (184)", "jmetal (190)", "dom4j (195)", "pdf-renderer (199)"))	
	

		ggplot(data751, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~instance)
		dev.off();
		

	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-10kaval-9metricas.pdf", sep = ""), width=8,height=8)
	data50 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data501 <-cbind (data50, "nmetricas" = str_count(data50$solutionreal,",")/2)
	
	data501$instance <- factor(data501$instance,      # Reordering group factor levels
                         levels = c("jstl (18)", "jnanoxml (25)", "seemp (31)", "apache-zip (36)", "notepad (46)", "udt-java (56)", "javaocr (59)", "servlet-api (63)", "forms (68)", "jscatterplot (74)", "jfluid (82)","junit (100)" ,"xmldom (119)" , "tinytim (134)","javacc (154)","java-geom (172)", "xmlapi (184)", "jmetal (190)", "dom4j (195)", "pdf-renderer (199)"))	
	
		ggplot(data501, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(.~instance)
		dev.off();
		
		
		

	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-20kaval-9metricas.pdf", sep = ""), width=8,height=8)
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-20kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data751 <-cbind (data75, "nmetricas" = str_count(data75$solutionreal,",")/2)

	data751$instance <- factor(data751$instance,      # Reordering group factor levels
                         levels = c("jstl (18)", "jnanoxml (25)", "seemp (31)", "apache-zip (36)", "notepad (46)", "udt-java (56)", "javaocr (59)", "servlet-api (63)", "forms (68)", "jscatterplot (74)", "jfluid (82)","junit (100)" ,"xmldom (119)" , "tinytim (134)","javacc (154)","java-geom (172)", "xmlapi (184)", "jmetal (190)", "dom4j (195)", "pdf-renderer (199)"))	
	

		ggplot(data751, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5, colour = "gray") + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~instance)
		dev.off();
		