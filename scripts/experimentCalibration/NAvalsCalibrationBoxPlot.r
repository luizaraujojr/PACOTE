install.packages("ggplot2")                  # Install & load ggplot2
library("ggplot2")

library("stringr");
library("reshape2");





	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-2-5kaval-9metricas.pdf", sep = ""), width=8,height=8)
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-2.5kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
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
		