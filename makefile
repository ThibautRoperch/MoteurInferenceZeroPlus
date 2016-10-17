#makefile

all:
	@echo "# Compilation des classes"
	find -name "*.java" | xargs javac -d bin

launch:
	@echo "# Execution de la classe Main"
	java -cp bin Main

clean:
	@echo "# Nettoyage du dossier bin"
	rm bin/*