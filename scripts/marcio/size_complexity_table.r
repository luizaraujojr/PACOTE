rm(list = ls())
library(tidyverse)

#
# Configuracao
#
project<-"JUnit";

#baseDirectory <- "D:/Backup/eclipse-workspace";
baseDirectory <- "C:/Users/User/Desktop/Codigos";


#
# Dados de tamanho
#
data <- read_tsv(paste0(baseDirectory, "/PACOTE/results/JARProjectCharacteristics", project, ".data"));

result1 <- data %>% 
            group_by(versions) %>% 
            summarize(
              packages = n_distinct(packages), 
              classes = n(), 
              attrs = sum(attrs), 
              meths = sum(meths), 
              pmeths = sum(pmeths));
  


#
# Carrega dados sobre as depend�ncias
#
data2 <- read_tsv(paste0(baseDirectory, "/PACOTE/results/ODEMProjectCharacteristics", project, ".data"));

# Apenas para o JHotdraw
if (project == "JHotDraw") {
  data2 <- data2 %>% mutate(versions = substr(version, 10, 14));
}
  
if (project == "JUnit") {
  data2 <- data2 %>% mutate(versions = version);
}

result2 <- data2 %>%
  mutate(cbo = round(cbo, 2)) %>%
  mutate(eff = round(eff, 2)) %>% 
  mutate(aff = round(aff, 2)) %>% 
  mutate(lcom = round(lcom5, 2)) %>% 
  mutate(depCount = round(dependencyCount, 2)) %>%
  select(versions, depCount);


#
# Junta as duas tabelas
#
result <- result1 %>% 
  inner_join(result2, by = c("versions"));


#
# Classes por pacote
#
summary(result$classes / result$packages);
sd(result$classes / result$packages);


#
# Variacao de classes por pacote
#
variation <- result %>%
  mutate(versions = str_c(lag(versions), " -> ", versions)) %>%
  mutate(rcp = ((classes / packages) / lag((classes / packages)) - 1) * 100) %>%
  select(versions, rcp) %>% 
  slice(2:n()) %>% 
  arrange(-rcp);


#
# Atributos por classe
#
summary(result$attrs / result$classes);
sd(result$attrs / result$classes);


#
# metodos por classe
#
summary(result$meths / result$classes);
sd(result$meths / result$classes);


#
# metodos publicos por classe
#
summary(result$pmeths / result$classes);
sd(result$pmeths / result$classes);


#
# Depend�ncias  por classe
#
summary(result$depCount / result$classes);
sd(result$depCount / result$classes);


#
# Boxplot de classes por pacote
#
boxplotClassesPacotes <- data %>% 
  group_by(versions, packages) %>% 
  summarize(classes = n());

ggplot(boxplotClassesPacotes, aes(x=versions, y=classes)) + 
  geom_boxplot() +
  labs(x = NULL, y = "Classes por Pacote") + 
  theme_bw()


#
# Boxplot de atributos, m�todos e m�todos p�blicos por classe
#
ggplot(data, aes(x=versions, y=attrs)) + 
  geom_boxplot() +
  labs(x = NULL, y = "Atributos por Classe") + 
  theme_bw();

ggplot(data, aes(x=versions, y=meths)) + 
  geom_boxplot() +
  labs(x = NULL, y = "M�todos por Classe") + 
  theme_bw();

ggplot(data, aes(x=versions, y=pmeths)) + 
  geom_boxplot() +
  labs(x = NULL, y = "M�todos p�blicos por Classe") + 
  theme_bw();


#
# Correlacao
#
round(cor(result %>% select(-versions),  method = "spearman"), 2);

#write.csv(result,file=paste(baseDirectory, "/PACOTE/data/table/", project, "_size_complexity.csv", sep = ""))
#write.csv(round(cor(result,  method = "spearman"),2),   file=paste(baseDirectory, "/PACOTE/data/table/", project, "_size_complexity_spearman.csv", sep = ""))
