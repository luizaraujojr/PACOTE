
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



data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/ODEMProjectCharacteristicsjhotdraw.data", header=TRUE);

versions <- sort(unique(data$version));

colnames <- c("CBO", "AFF", "EFF", "LCOM", "MQ", "EVM","elegance", "dependencyCount" );
result2 <- matrix(nrow=length(versions), ncol=length(colnames), dimnames=list(versions, colnames));

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	
	result2[version_, "CBO"] <- round(mean(vdata$cbo),2);
	result2[version_, "EFF"] <- round(mean(vdata$eff),2);
	result2[version_, "AFF"] <- round(mean(vdata$aff),2);
	result2[version_, "LCOM"] <- round(mean(vdata[complete.cases(vdata), ]$lcom),2);
	result2[version_, "MQ"] <- round(sum(vdata$mq),2);	
	result2[version_, "EVM"] <- round(sum(vdata$evm),2);	
	result2[version_, "elegance"] <- round(mean(vdata$elegance),2);
	result2[version_, "dependencyCount"] <- round(mean(vdata$dependencyCount),2);
}

result2

result = data.frame(result1, result2)


round(cor(result,  method = "spearman"),2)






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

publishNormal(shapiro.test(result1[,"Packages"])$p.value, "numero de pacotes");
publishNormal(shapiro.test(result1[,"Classes"])$p.value, "numero de classes");
publishNormal(shapiro.test(result1[,"Attrs"])$p.value, "numero de atributos");
publishNormal(shapiro.test(result1[,"Meths"])$p.value, "numero de metodos");
publishNormal(shapiro.test(result1[,"PMeths"])$p.value, "numero de metodos públicos");


# Normality tests
p <- c(4, 13, 8, 13, 13, 21, 21, 21, 21, 21, 24, 24, 24, 25, 25, 25, 29, 29, 30, 30, 59, 59, 59, 60);
publishNormal(shapiro.test(result1[,"Classes")$p.value, "numero de pacotes");

c <- c(102, 173, 187, 265, 265, 401, 401, 406, 407, 407, 523, 524, 553, 576, 576, 576, 752, 769, 870, 873, 1090, 1093, 1094, 1116);
publishNormal(shapiro.test(result1[,"Classes"])$p.value, "numero de classes");

a <- c(141, 249, 294, 410, 410, 584, 584, 591, 593, 597, 797, 797, 843, 880, 880, 880, 1162, 1191, 1361, 1363, 1719, 1723, 1725, 1875);
publishNormal(shapiro.test(a)$p.value, "numero de atributos");

m <- c(816, 1521, 1548, 2193, 2202, 3379, 3389, 3457, 3465, 3465, 4651, 4679, 4884, 5124, 5126, 5129, 6581, 6793, 7725, 7770, 9879, 9922, 9934, 10103);
publishNormal(shapiro.test(m)$p.value, "numero de metodos");

pm <- c(633, 1186, 1223, 1729, 1735, 2639, 2644, 2703, 2708, 2708, 3639, 3665, 3817, 3971, 3973, 3973, 4978, 5088, 5686, 5720, 7248, 7275, 7275, 7475);
publishNormal(shapiro.test(pm)$p.value, "numero de metodos publicos");

d <- c(362, 803, 855, 1228, 1230, 1893, 1894, 1942, 1947, 1947, 2557, 2562, 2445, 2566, 2566, 2568, 3505, 3583, 4127, 4138, 5329, 5351, 5354, 5522);
publishNormal(shapiro.test(d)$p.value, "numero de dependencias");

nac <- c(8.305131, 11.919944, 13.014406, 16.849349, 16.849349, 20.834375, 20.834375, 21.118125, 21.231429, 21.231429, 26.048523, 26.132067, 27.700993, 29.272665, 29.272665, 29.272665, 34.451254, 34.936447, 38.476841, 38.567758, 38.27966, 38.35064, 38.346258, 37.336256);
publishNormal(shapiro.test(nac)$p.value, "elegancia de classes");

	
	