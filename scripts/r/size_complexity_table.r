<<<<<<< HEAD
project<-"JHotDraw";
=======
	project<-"JEdit"
>>>>>>> parent of b93b78e (review)

#baseDirectory <- "D:/Backup/eclipse-workspace";
baseDirectory <- "C:/Users/User/Desktop/Codigos";

data <- read.table(paste(baseDirectory, "/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);

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


data <- read.table(paste(baseDirectory, "/PACOTE/results/ODEMProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
versions <- sort(unique(data$version));

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "dependencyCount" );
result2 <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	
	result2[version_, "CBO"] <- round(mean(vdata$cbo),2);
	result2[version_, "EFF"] <- round(mean(vdata$eff),2);
	result2[version_, "AFF"] <- round(mean(vdata$aff),2);
	result2[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom5),2);
	result2[version_, "MQ"] <- round(sum(vdata$mq),2);	
	result2[version_, "dependencyCount"] <- round(mean(vdata$dependencyCount),2);
}

result2

result = data.frame(result1, "dep_class"=round(result2[,6]/result1[,2],2))


<<<<<<< HEAD
write.csv(result,file=paste(baseDirectory, "/PACOTE/data/table/", project, "_size_complexity.csv", sep = ""))

write.csv(round(cor(result,  method = "spearman"),2),   file=paste(baseDirectory, "/PACOTE/data/table/", project, "_size_complexity_spearman.csv", sep = ""))
=======
write.csv(result,file=paste("D:/Backup/eclipse-workspace/PACOTE/results/size_complexity", project, ".csv", sep = ""))

write.csv(round(cor(result,  method = "spearman"),2),   file=paste("D:/Backup/eclipse-workspace/PACOTE/results/size_complexity_spearman", project, ".csv", sep = ""))
>>>>>>> parent of b93b78e (review)

#
# Classes por pacote
#
round(mean(result[,2]/result[,1]),2)
round(sd(result[,2]/result[,1]),2)
round(median(result[,2]/result[,1]),2)
round(min(result[,2]/result[,1]),2)
round(max(result[,2]/result[,1]),2)


#
# Atributos por classe
#
round(mean(result[,3]/result[,2]),2)
round(sd(result[,3]/result[,2]),2)
round(median(result[,3]/result[,2]),2)
round(min(result[,3]/result[,2]),2)
round(max(result[,3]/result[,2]),2)


#
# metodos por classe
#
round(mean(result[,4]/result[,2]),2)
round(sd(result[,4]/result[,2]),2)
round(median(result[,4]/result[,2]),2)
round(min(result[,4]/result[,2]),2)
round(max(result[,4]/result[,2]),2)


#
# metodos públicos por classe
#
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



write.csv(result,   file=paste("D:/Backup/eclipse-workspace/PACOTE/results/size_complexity_spearman", project, ".csv", sep = ""))








	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/JARProjectCharacteristicsJhotdraw.data", header=TRUE);
	unique_versions <- unique(data$versions);
	colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths", "NAC");
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
		result1[version_, "NAC"] <- sd(unlist(lapply(classes, nrow)));
	}
	result1



# Publishes results from the normality test (95%)
publishNormal <- function(pvalue, name) {
	if (pvalue < 0.05) {
		cat("Existem evidências de que a distribuição de ", name, " não é normal (", pvalue, ").\n", sep="");
	} else {
		cat("Nao e possivel negar que a distribuição de ", name, " é normal (", pvalue, ").\n", sep="");
	}
}


#Analisando a interseção entre os pacotes das versões
		pdf("D:/Backup/eclipse-workspace/pacote/results/JHotDraw_version_intersect.pdf", width=8,height=5)
		library(arsenal)
		
		#par(mfrow=c(4, 4), mar = c(0.2, 0.2, 0.2, 0.2), mai=c(0,0,0,0))
		par(mfrow=c(3, 5), mai=c(0,0,0,0))
		data <- read.table("D:/Backup/eclipse-workspace/pacote/results/JARProjectCharacteristicsJhotdraw.data", header=TRUE);
		unique_versions <- unique(data$versions);
		colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths", "NAC");
		result1 <- matrix(nrow=length(unique_versions), ncol=length(colnames), dimnames=list(unique_versions, colnames));
		previous_version <- "";
		for (version_ in unique_versions)
		{
			if (previous_version != "") {
				first <- unique(subset(data, versions == previous_version)$packages);
				second <- unique(subset(data, versions == version_)$packages);
				
				both <-  intersect(first, second)
				onlyfirst <- setdiff(first, second)
				onlysecond <- setdiff(second, first)
				
				require("gplots")
				
				list_ <- list(second, first)
				names(list_) <- c(version_, previous_version)
				venn(list_)
			}
			previous_version <- version_;
		}
		dev.off();
	
	