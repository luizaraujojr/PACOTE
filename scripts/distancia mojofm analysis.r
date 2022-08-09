install.packages("ggplot2")                  # Install & load ggplot2
library("ggplot2")

library("stringr");
library("reshape2");


dataJHotDraw <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_jhotdraw-4metricas20k-50pertub.txt", sep=";", header=TRUE);
	
dataJEdit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_jedit-4metricas20k-50pertub.txt", sep=";", header=TRUE);

dataJUnit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_junit-4metricas20k-50pertub.txt", sep=";", header=TRUE);

data <-rbind (dataJHotDraw, dataJEdit, dataJUnit)

pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/histogram-distancias-mojofm.pdf", sep = ""), width=8,height=4)

par(mfrow= c(1,3))
hist(dataJHotDraw$distance, xlab="Distância JHotDraw", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))
hist(dataJEdit$distance, xlab="Distância JEdit", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))
hist(dataJUnit$distance, xlab="Distância JUnit", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))


dev.off();





dataJHotDraw <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_jhotdraw-3metricas20k-50pertub.txt", sep=";", header=TRUE);
	
dataJEdit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_jedit-3metricas20k-50pertub.txt", sep=";", header=TRUE);

dataJUnit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/distancia mojofm/distancia_mojo_junit-3metricas20k-50pertub.txt", sep=";", header=TRUE);

data <-rbind (dataJHotDraw, dataJEdit, dataJUnit)

pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/histogram-distancias-mojofm-3metricas.pdf", sep = ""), width=8,height=4)

par(mfrow= c(1,3))
hist(dataJHotDraw$distance, xlab="Distância JHotDraw", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))
hist(dataJEdit$distance, xlab="Distância JEdit", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))
hist(dataJUnit$distance, xlab="Distância JUnit", main="", ylab="Frequência", xlim=c(0,50), ylim=c(0,50))


dev.off();