# LocalZero Auth JSON Store

This project stores users in a local JSON file and hashes passwords with BCrypt.

## Files
- `data/users.json` is the local user store.
- `src/main/java/server/security/JsonUserStore.java` handles JSON persistence.
- `src/main/java/server/service/AccountService.java` hashes and verifies passwords.

## Quick Demo
```zsh
mvn -q -DskipTests compile
mvn -q -DskipTests exec:java -Dexec.mainClass=app.LocalZeroAuthDemo
```

## Notes
- If `data/users.json` is deleted, it will be recreated on first write.
- Passwords are stored as BCrypt hashes.

