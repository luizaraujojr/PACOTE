install.packages("ggplot2")                  # Install & load ggplot2
install.packages("gridExtra")

library("gridExtra")
library("ggplot2")

library("stringr");
library("reshape2");


dataJEdit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/3metricas-20kEvals-JEDIT-050Perturb-DEVref.csv", sep=";", header=TRUE);
dataJHotDraw <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/3metricas-20kEvals-JHOTDRAW-050Perturb-DEVref.csv", sep=";", header=TRUE);
dataJUnit <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Experiment/result/3metricas-20kEvals-JUNIT-050Perturb-DEVref.csv", sep=";", header=TRUE);

data <-rbind (dataJHotDraw, dataJEdit, dataJUnit)
	
data1 <-cbind (data, "software" = str_split( data$instance, "-", simplify= TRUE )[,1])


pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/boxplot-software-ref-3metricas.pdf", sep = ""), width=8,height=4)

ggplot(data1, aes(x=software, y=mojo)) +
geom_boxplot() +
labs(x="Softwares de referência", y = "% de MoJoFM")

dev.off();


pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/histograma-coeficientes-3metricas.pdf", sep = ""), width=8,height=4)

par(mfrow= c(2,3))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,1]), xlab="Coeficiente A1", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,2]), xlab="Coeficiente A2", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,3]), xlab="Coeficiente A3", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))

hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,4]), xlab="Coeficiente B1", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,5]), xlab="Coeficiente B2", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))
hist(as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", data1$solutionreal)), ",", simplify= TRUE)[,6]), xlab="Coeficiente B3", main="", ylab="Frequência", breaks=11, xlim=c(-2.5,+2.5), ylim=c(0,10))

dev.off();


softwares <- sort(unique(data1$software));
	
columns <- c("Máximo", "Média", "Mínimo", "Valor MQ");

result <- matrix(nrow=length(softwares), ncol=length(columns), dimnames=list(softwares, columns));

for (software_ in softwares)
{
	newdata <- subset(data1, software == software_);

	result[software_, "Máximo"] <- max(newdata$mojo);
	result[software_, "Média"] <- paste(round(mean(newdata$mojo),3),"+-",round(sd(newdata$mojo),4));
	result[software_, "Mínimo"] <- min(newdata$mojo);
}
		
result



pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/experiment/result/boxplot-coeficientes-3metricas.pdf", sep = ""), width=8,height=4)

p1 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,1]))) +
geom_boxplot()  + ylim(-2.5,+2.5) + xlab("Coeficiente A1") + ylab("")


p2 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,2]))) +
geom_boxplot()  + ylim(-2.5,+2.5) + xlab("Coeficiente A2") + ylab("")


p3 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,3]))) +
geom_boxplot()   + ylim(-2.5,+2.5)+ xlab("Coeficiente A3") + ylab("")

p4 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,4]))) +
geom_boxplot()   + ylim(-2.5,+2.5)+ xlab("Coeficiente B1") + ylab("")

p5 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,5]))) +
geom_boxplot()   + ylim(-2.5,+2.5)+ xlab("Coeficiente B2") + ylab("")

p6 <- ggplot(data1, aes(x=software, y=as.numeric(str_split(gsub("[[]", "", gsub("[,]]", "", solutionreal)), ",", simplify= TRUE)[,6]))) +
geom_boxplot()   + ylim(-2.5,+2.5)+ xlab("Coeficiente B3") + ylab("")


grid.arrange(p1, p2, p3, p4, p5, p6, ncol=3)
dev.off();