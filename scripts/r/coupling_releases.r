
data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics.data", header=TRUE);

JHotDraw_Exclude <- c("5.4.2");
JEdit_Exclude <- c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2","5.4.0");


`%notin%` <- Negate(`%in%`)
	
	
if (project=="JHotDraw"	){
	data <- subset(data, versions %notin% JHotDraw_Exclude)
}

if (project=="JEdit"	){
	data <- subset(data, versions %notin% JEdit_Exclude)
}

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
