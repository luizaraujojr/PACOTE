	project<-"JHotDraw"

	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);

	unique_versions <- unique(data$versions);
	colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths");
	result1 <- matrix(nrow=length(unique_versions), ncol=length(colnames), dimnames=list(unique_versions, colnames));

	for (version_ in unique_versions)
	{
		vdata <- subset(data, versions == version_);
		classes <- split(vdata, vdata$package);
		
		result1[version_, "Packages"] <- length(unique(vdata$packages));
		result1[version_, "Classes"] <- length(vdata$classes);
		result1[version_, "Attrs"] <- sum(vdata$attrs);
		result1[version_, "Meths"] <- sum(vdata$meths);
		result1[version_, "PMeths"] <- sum(vdata$pmeth);
	}
	result1


	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/ODEMProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	versions <- sort(unique(data$version));

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "dependencyCount" );
result2 <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, versions == version_);
	
	result2[version_, "CBO"] <- round(mean(vdata$cbo),2);
	result2[version_, "EFF"] <- round(mean(vdata$eff),2);
	result2[version_, "AFF"] <- round(mean(vdata$aff),2);
	result2[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom5),2);
	result2[version_, "MQ"] <- round(sum(vdata$mq),2);	
	result2[version_, "dependencyCount"] <- round(mean(vdata$dependencyCount),2);
}

result2

#result = data.frame(result1, "dep_class"=round(result2[,6]/result1[,2],2))
	result = data.frame(result1, "dependencyCount"=round(result2[,6],2))


write.csv(result,file=paste("D:/Backup/eclipse-workspace/PACOTE/data/table/", project, "_size_complexity.csv", sep = ""))

write.csv(round(cor(result,  method = "spearman"),2),   file=paste("D:/Backup/eclipse-workspace/PACOTE/data/table/", project, "_size_complexity_spearman.csv", sep = ""))

#classes por pacote
round(mean(result[,2]/result[,1]),2)
round(sd(result[,2]/result[,1]),2)
round(median(result[,2]/result[,1]),2)
round(min(result[,2]/result[,1]),2)
round(max(result[,2]/result[,1]),2)

#atributos por classe
round(mean(result[,3]/result[,2]),2)
round(sd(result[,3]/result[,2]),2)
round(median(result[,3]/result[,2]),2)
round(min(result[,3]/result[,2]),2)
round(max(result[,3]/result[,2]),2)


#metodos por classe
round(mean(result[,4]/result[,2]),2)
round(sd(result[,4]/result[,2]),2)
round(median(result[,4]/result[,2]),2)
round(min(result[,4]/result[,2]),2)
round(max(result[,4]/result[,2]),2)



#metodos públicos por classe
round(mean(result[,5]/result[,2]),2)
round(sd(result[,5]/result[,2]),2)
round(median(result[,5]/result[,2]),2)
round(min(result[,5]/result[,2]),2)
round(max(result[,5]/result[,2]),2)


#dependências
round(mean(result[,6]),2)
round(sd(result[,6]),2)
round(median(result[,6]),2)
round(min(result[,6]),2)
round(max(result[,6]),2)