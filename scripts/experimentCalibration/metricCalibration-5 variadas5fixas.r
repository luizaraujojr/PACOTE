library("ggplot2")

library("stringr");
library("reshape2");

data1 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/5metricas20variacoes-20kEvals-20instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
data1new <-cbind (data1, "Cenário" = "Combinação variada das 5 métricas")


data2 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/5metricas-20kEvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);

data2new <-cbind (data2, "metricconfig" = "metricconfig")
data2new1 <-cbind (data2new, "Cenário" = "Combinação padrão das 5 métricas")

data <- rbind(data1new, data2new1)
	
	
pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-metric-fixad-variable20keval-5metricas.pdf", sep = ""), width=8,height=8)

ggplot(data, aes(factor(nclasses), mojo)) +
     geom_boxplot(aes(fill = Cenário)) +
	 geom_vline(xintercept = (0:20)+0.5) + 
	 xlab("Número de Classes") + 
	ylab("MoJoFM") + 
    theme(legend.position="bottom")
	
	
	 	dev.off();
