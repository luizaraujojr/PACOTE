#install.packages("ggplot2")                  # Install & load ggplot2
library("ggplot2")

library("stringr");
library("reshape2");


RIGHT = function(x,n){
  substring(x,nchar(x)-n+1)
}

		data <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-20kAvals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);

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
		
		
		#carregando os dados de todas os 4 cenários
		
		data2.5 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-2.5kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
		data5 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-5kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
		data10 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-10kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
		data20 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-20kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
		data50 <- read.table("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/2-9metricas-50kavals-20Instancias-050Perturb-MQref.csv", sep=";", header=TRUE);
				
		numClasses <- sort(unique(RIGHT(paste("00", as.character(data5$nclasses), sep = ""), 3)));
		
		nummetrics <- sort(unique(as.character(str_count(data5[,4],",")/2)));

		#columns <- c("2.5->5","5->10","10->20");

		result5 <- matrix(nrow=length(numClasses), ncol=length(nummetrics), dimnames=list(numClasses, nummetrics));
		
		result10 <- matrix(nrow=length(numClasses), ncol=length(nummetrics), dimnames=list(numClasses, nummetrics));
		
		result20 <- matrix(nrow=length(numClasses), ncol=length(nummetrics), dimnames=list(numClasses, nummetrics));
		
		for (numClasses_ in numClasses)
		{
			for (nummetrics_ in nummetrics)
			{
				newdata2.5 <- subset(data2.5, nclasses == as.numeric(numClasses_) & (str_count(data2.5$solutionreal,",")/2) == nummetrics_);
				newdata5 <- subset(data5, nclasses == as.numeric(numClasses_) & (str_count(data5$solutionreal,",")/2) == nummetrics_);
				newdata10 <- subset(data10, nclasses == as.numeric(numClasses_) & (str_count(data10$solutionreal,",")/2) == nummetrics_);
				newdata20 <- subset(data20, nclasses == as.numeric(numClasses_) & (str_count(data20$solutionreal,",")/2) == nummetrics_);
				newdata50 <- subset(data50, nclasses == as.numeric(numClasses_) & (str_count(data50$solutionreal,",")/2) == nummetrics_);
				
				#montando as tabelas de análise de crescimento result5 = 2,5 -> 5, result10 = 5 -> 10, result20 = 10->20, result50 = 20->50		
				result5[numClasses_, nummetrics_] <- (mean(newdata5$mojo) / mean(newdata2.5$mojo))-1;
				result10[numClasses_, nummetrics_] <- (mean(newdata10$mojo) / mean(newdata5$mojo))-1;
				result20[numClasses_, nummetrics_] <- (mean(newdata20$mojo) / mean(newdata10$mojo))-1;
				result50[numClasses_, nummetrics_] <- (mean(newdata50$mojo) / mean(newdata20$mojo))-1;
		
			}
		}
				
		
		#montando a tabela de comparação para a variação de número de classes
		
		columns <- c("2.5->5","5->10","10->20", "20->50" );
		resultnclasses <- matrix(nrow=length(numClasses), ncol=length(columns), dimnames=list(numClasses, columns));
		
		for (numClasses_ in numClasses)
		{
			resultnclasses[numClasses_, 1] <- mean(result5[numClasses_,]);
			resultnclasses[numClasses_, 2] <- mean(result10[numClasses_,]);
			resultnclasses[numClasses_, 3] <- mean(result20[numClasses_,]);
			resultnclasses[numClasses_, 4] <- mean(result50[numClasses_,]);
		}
		
		#montando a tabela de comparação para a variação de número de métricas
		
		resultnmetrics <- matrix(nrow=length(nummetrics), ncol=length(columns), dimnames=list(nummetrics, columns));
		
		for (nummetrics_ in nummetrics)
		{
			resultnmetrics[nummetrics_, 1] <- mean(result5[,nummetrics_]);
			resultnmetrics[nummetrics_, 2] <- mean(result10[,nummetrics_]);
			resultnmetrics[nummetrics_, 3] <- mean(result20[,nummetrics_]);
			resultnmetrics[nummetrics_, 4] <- mean(result50[,nummetrics_]);
		}
		

		# executando os testes de normalidade para as distribuições de dados de crescimento	
		
		shapiro.test(as.numeric(numClasses))
		qqnorm(as.numeric(numClasses))
		
		shapiro.test(as.numeric(resultnclasses[,1]))	
		shapiro.test(as.numeric(resultnclasses[,2]))
		shapiro.test(as.numeric(resultnclasses[,3]))
		shapiro.test(as.numeric(resultnclasses[,4]))
		
		shapiro.test(as.numeric(nummetrics))
		shapiro.test(as.numeric(resultnmetrics[,1]))
		shapiro.test(as.numeric(resultnmetrics[,2]))
		shapiro.test(as.numeric(resultnmetrics[,3]))
		shapiro.test(as.numeric(resultnmetrics[,4]))
		
		pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/normal_analysis_kaval_classes_graph.pdf", sep = ""), width=8,height=2.5)

		par(mfrow=c(1,3))
		
		hist(as.numeric(resultnclasses[,1]), main="2,5 -> 5", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnclasses[,2]), main="5 -> 10", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnclasses[,3]), main="10 -> 20", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnclasses[,4]), main="20 -> 50", xlab="% de aumento do MoJoFM", ylab="Frequência");
		dev.off();
		
		
		pdf(paste("C:/Users/luizantoniolao/Documents/GitHub/PACOTE/ils-clustering-main-experiment/data/Calibração/normal_analysis_kaval_metric_graph.pdf", sep = ""), width=8,height=2.5)

		par(mfrow=c(1,3))
		
		hist(as.numeric(resultnmetrics[,1]), main="2,5 -> 5", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnmetrics[,2]), main="5 -> 10", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnmetrics[,3]), main="10 -> 20", xlab="% de aumento do MoJoFM", ylab="Frequência");
		hist(as.numeric(resultnmetrics[,4]), main="20 -> 50", xlab="% de aumento do MoJoFM", ylab="Frequência");
		
		dev.off();

		cor(as.numeric(numClasses), resultnclasses[,], method = "spearman")
		cor(as.numeric(nummetrics), resultnmetrics[,], method = "spearman")
