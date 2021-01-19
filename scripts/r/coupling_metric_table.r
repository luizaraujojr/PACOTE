
	project <-"JHotDraw"
		
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/ODEMPackageCharacteristics", project, ".data", sep = ""), header=TRUE);


	JHotDraw_Exclude <- c("5.4.2", "7.2.0");
	JEdit_Exclude <- c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2");
	JUnit_Exclude <- c("4.08.0");


	`%notin%` <- Negate(`%in%`)
		
	data$versions <- as.character(data$versions)	
	if (project=="JHotDraw"	){
		data <- subset(data, versions %notin% JHotDraw_Exclude)
	}

	if (project=="JEdit"){
		data <- subset(data, versions %notin% JEdit_Exclude)
	}

	if (project=="JUnit"){
		data <- subset(data, versions %notin% JUnit_Exclude)
	}

	data$versions <- as.factor(data$versions)

	versions <- sort(unique(data$versions));

	colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ");
	result <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

	for (version_ in versions)
	{
		vdata <- subset(data, versions == version_);
		
		result[version_, "CBO"] <- round(mean(vdata$cbo),2);
		result[version_, "EFF"] <- round(mean(vdata$eff),2);
		result[version_, "AFF"] <- round(mean(vdata$aff),2);
		result[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom),2);
		result[version_, "MQ"] <- round(sum(vdata$mf),2);	

	}

	result



	write.csv(result,file=paste("D:/Backup/eclipse-workspace/PACOTE/data/table/", project, "_coupling_metric_table.csv", sep = ""))


	#cheking the correlation

	write.csv(round(cor(result,  method = "spearman"),2),file=paste("D:/Backup/eclipse-workspace/PACOTE/data/table/", project, "_coupling_metric_table_spearman.csv", sep = ""))

