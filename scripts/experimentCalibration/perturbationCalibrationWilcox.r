right = function (string, char) {
    substr(string,nchar(string)-(char-1),nchar(string))
}


	data25 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-025Perturb-MQref.csv", sep=";", header=TRUE);
	data50 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-075Perturb-MQref.csv", sep=";", header=TRUE);
	
	numClasses <- sort(unique(right(paste("00", as.character(data25$nclasses), sep = ""), 3)));
	
	columns <- sort(unique(as.character(str_count(data25[,4],",")/2)));

	result <- matrix(nrow=length(numClasses), ncol=length(columns), dimnames=list(numClasses, columns));
	
	resultwilcox <- matrix(nrow=length(numClasses), ncol=length(columns), dimnames=list(numClasses, columns));
	
	
	for (numClasses_ in numClasses)
	{
		for (columns_ in columns)
		{
			newdata25 <- subset(data25, nclasses == as.numeric(numClasses_) & (str_count(data25$solutionreal,",")/2) == columns_);
			newdata50 <- subset(data50, nclasses == as.numeric(numClasses_) & (str_count(data50$solutionreal,",")/2) == columns_);
			newdata75 <- subset(data75, nclasses == as.numeric(numClasses_) & (str_count(data75$solutionreal,",")/2) == columns_);
			
			if (mean(newdata25$mojo)> mean(newdata50$mojo)){
				if (mean(newdata25$mojo)> mean(newdata75$mojo)){
					if(mean(newdata50$mojo)> mean(newdata75$mojo)){
						bestPertub <- "25>50"
						resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata25$mojo,newdata50$mojo)$p.value
					} else {
						bestPertub <- "25>75"
						resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata25$mojo,newdata75$mojo)$p.value
					}
					
				} else {	
					bestPertub <- "75>25"	
					resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata75$mojo,newdata25$mojo)$p.value					
				}
			} else {
				if (mean(newdata50$mojo)> mean(newdata75$mojo)){
					if(mean(newdata75$mojo)> mean(newdata25$mojo)){
						bestPertub <- "50>75"
						resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata50$mojo,newdata75$mojo)$p.value						
					}
					else {
						bestPertub <- "50>25"
						resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata50$mojo,newdata25$mojo)$p.value
					}
				}
				else {
					bestPertub <- "75>50"
					resultwilcox[numClasses_, columns_] <-  wilcox.test(newdata75$mojo,newdata50$mojo)$p.value
				}
			}
			result[numClasses_, columns_] <- bestPertub;
		}
	}
	
	
	library("ggplot2")
library("stringr") 


	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub25-10keval-9metricas.pdf", sep = ""), width=8,height=8)
	data25 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-025Perturb-MQref.csv", sep=";", header=TRUE);
	data251 <-cbind (data25, "nmetricas" = str_count(data25$solutionreal,",")/2)
		ggplot(data251, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5) + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~nclasses)
		dev.off();


	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub50-10keval-9metricas.pdf", sep = ""), width=8,height=8)
	data50 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
	data501 <-cbind (data50, "nmetricas" = str_count(data50$solutionreal,",")/2)
		ggplot(data501, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5) + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~nclasses)
		dev.off();

		
	pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/boxplot-pertub75-10keval-9metricas.pdf", sep = ""), width=8,height=8)
	data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kEvals-20Instancias-075Perturb-MQref.csv", sep=";", header=TRUE);
	data751 <-cbind (data75, "nmetricas" = str_count(data75$solutionreal,",")/2)

		ggplot(data751, aes(factor(nmetricas), as.numeric(mojo))) +
		 geom_boxplot(aes(fill = factor(nmetricas))) + 
		  geom_vline(xintercept = (0:20)+0.5) + 
		  xlab("Número de Métricas") + 
		  ylab("% de MoJoFM") + 
		  theme(legend.position="none") +
		facet_wrap(~nclasses)
		dev.off();
		
		
		
