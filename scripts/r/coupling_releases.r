
data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics.data", header=TRUE);

versions <- sort(unique(data$version));

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "MF", "EVM", "CS");
result <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	
	result[version_, "CBO"] <- round(mean(vdata$cbo),2);
	result[version_, "EFF"] <- round(mean(vdata$eff),2);
	result[version_, "AFF"] <- round(mean(vdata$aff),2);
	result[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom),2);
	result[version_, "MQ"] <- round(sum(vdata$mf),2);	
	result[version_, "MF"] <- round(mean(vdata$mf),2);	
	result[version_, "EVM"] <- round(sum(vdata$cs),2);	
	result[version_, "CS"] <- round(mean(vdata$cs),2);
}

result

mean(result[,"MF"]);
sd(result[,"MF"]);
median(result[,"MF"]);


mean(result[,"CS"]);
sd(result[,"CS"]);
median(result[,"CS"]);

#cheking the correlation

round(cor(result,  method = "spearman"),2)
