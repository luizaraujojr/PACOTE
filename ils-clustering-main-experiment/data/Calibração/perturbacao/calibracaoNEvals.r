install.packages("ggplot2")                  # Install & load ggplot2
library("ggplot2")

library("stringr");
library("reshape2");


RIGHT = function(x,n){
  substring(x,nchar(x)-n+1)
}

		data <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/perturbacao/2-9metricas-20kEvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);

		#data25 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/perturbacao/2-9metricas-10kEvals-20Instancias-025Perturb-MQref.csv", sep=";", header=TRUE);
		#data50 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/perturbacao/2-9metricas-10kEvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
		#data75 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/perturbacao/2-9metricas-10kEvals-20Instancias-075Perturb-MQref.csv", sep=";", header=TRUE);
		
		numClasses <- sort(unique(RIGHT(paste("00", as.character(data$nclasses), sep = ""), 3)));
		
		columns <- sort(unique(as.character(str_count(data[,4],",")/2)));

		result <- matrix(nrow=length(numClasses), ncol=length(columns), dimnames=list(numClasses, columns));
		
		resultwilcox <- matrix(nrow=length(numClasses), ncol=length(columns), dimnames=list(numClasses, columns));
		
		for (numClasses_ in numClasses)
		{
			for (columns_ in columns)
			{
				newdata <- subset(data, nclasses == as.numeric(numClasses_) & (str_count(data$solutionreal,",")/2) == columns_);
				
				result[numClasses_, columns_] <- mean(newdata$mojo);
			}
		}
		
		result;
		