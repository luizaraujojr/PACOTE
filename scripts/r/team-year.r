project<-"JEdit"

data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_RevisionsByYear.data", sep = ""), header=TRUE);

years <- sort(unique(data$year));
columns <- c("team", "inTeam", "outTeam", "num_commits");
result <- matrix(nrow=length(years), ncol=length(columns), dimnames=list(years, columns));

oldTeam <- c();

for (year_ in years)
{
	vdata <- subset(data, year == year_);
	developers <- split(vdata, vdata$author);

	team <- unique(vdata$author);
	inTeam <- setdiff(team, oldTeam);
	outTeam <- setdiff(oldTeam, team);
	oldTeam <- team;

	commits <- unlist(lapply(developers, nrow));
	commits <- subset(commits, commits > 0);
	print(commits);

	result[year_ - 2000 + 1, "team"] <- length(team);
	result[year_ - 2000 + 1, "inTeam"] <- length(inTeam);
	result[year_ - 2000 + 1, "outTeam"] <- length(outTeam);
	result[year_ - 2000 + 1, "num_commits"] <- nrow(vdata);
}

result

write.csv(result,file=paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_team_year.csv", sep = ""))

