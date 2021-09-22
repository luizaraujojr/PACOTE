
project<-"JEdit"

data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_RevisionsByVersion.data", sep = ""), header=TRUE);

versions <- sort(unique(data$versions));

columns <- c("team", "inTeam", "outTeam", "num_commits");
result <- matrix(nrow=length(versions), ncol=length(columns), dimnames=list(versions, columns));

oldTeam <- c();

for (version_ in versions)
{
	vdata <- subset(data, versions == version_);
	developers <- split(vdata, vdata$author);

	team <- unique(vdata$author);
	inTeam <- setdiff(team, oldTeam);
	outTeam <- setdiff(oldTeam, team);
	
	oldTeam <- team;

	commits <- unlist(lapply(developers, nrow));
	commits <- subset(commits, commits > 0);
	print(commits);

	result[version_, "team"] <- length(team);
	result[version_, "inTeam"] <- length(inTeam);
	result[version_, "outTeam"] <- length(outTeam);
	result[version_, "num_commits"] <- nrow(vdata);
	#result[version_, "NAR"] <- round(sd(commits),2);
}

result

write.csv(result,file=paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_team_version.csv", sep = ""))

