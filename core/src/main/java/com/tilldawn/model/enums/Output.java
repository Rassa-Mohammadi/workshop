package com.tilldawn.model.enums;

import com.tilldawn.model.App;

public enum Output {
    // fields
    EnterUsername("Enter username", "Entrez un nom d'utilisateur"),
    EnterPassword("Enter password", "Entrez un mot de passe"),
    EnterPasswordAgain("Enter password again", "Entrez à nouveau le mot de passe"),
    Answer("Answer", "La Réponse"),
    EnterNewUsername("Enter new username", "Entrez un nouveau nom d'utilisateur"),
    EnterNewPassword("Enter new password", "Entrez un nouveau mot de passe"),
    EnterOldPassword("Enter old password", "Entrez l'ancien mot de passe"),
    // buttons
    Register("Register", "S'inscrire"),
    Login("Login", "Se connecter"),
    Exit("Exit", "Quitter"),
    Back("Back", "Retour"),
    Submit("Submit", "Soumettre"),
    Settings("Settings", "Paramètres"),
    Profile("Profile", "Profil"),
    Scoreboard("Scoreboard", "Tableau des scores"),
    Pregame("Pregame", "Avant-match"),
    Hint("Hint", "Indice"),
    LoadGame("Load game", "Charger le jeu"),
    ChangeLanguage("Change Language", "Changer de Langue"),
    PlayAsGuest("Play as Guest", "jouer en tant qu'invité"),
    Logout("Logout", "Déconnexion"),
    ChangeUsername("Change username", "Changer le nom d'utilisateur"),
    ChangePassword("Change password", "Changer le mot de passe"),
    Avatar("Avatar", "Avatar"),
    OpenFiles("Open Files", "Ouvrir des fichiers"),
    DeleteAcount("Delete Acount", "Supprimer le compte"),
    ForgotPassword("forgot password?", "mot de passe oublié?"),
    Play("Play", "Jouer"),
    AbilityDescription("Abilities Description", "Description des capacités"),
    CheatCodes("Cheat codes", "Codes de triche"),
    Resume("Resume", "Reprendre"),
    GiveUp("Give Up", "Abandonner"),
    Save("Save", "Sauvegarder"),
    Continue("Continue", "Continuer"),
    // titles
    RegisterMenu("Register Menu", "Menu d'inscription"),
    LoginMenu("Login Menu", "Menu de connexion"),
    MainMenu("Main Menu", "Menu principal"),
    SettingMenu("Setting Menu", "Menu de configuration"),
    RecoverPassword("Recover Password", "Récupérer le mot de passe"),
    ProfileMenu("Profile Menu", "Menu de profil"),
    AvatarMenu("Avatar Menu", "Menu de avatar"),
    PregameMenu("Pregame Menu", "Menu d'avant-match"),
    HintMenu("Hint Menu", "Menu d'indices"),
    AbilitiesMenu("Abilities Menu", "Menu des capacités"),
    CheatCodesMenu("Cheat Codes Menu", "Menu des codes de triche"),
    PauseMenu("Pause Menu", "Menu de pause"),
    ChooseAbility("Choose Ability", "Choisir la capacité"),
    // errors
    UsernameExists("Username already exists", "Ce nom d'utilisateur existe déjà"),
    PasswordEmpty("Password is empty", "Le mot de passe est vide"),
    AnswerEmpty("Answer is empty", "La réponse est vide"),
    ReenterPasswordError("Reenter password doesn't match", "Les mots de passe ne correspondent pas"),
    PasswordLength("Password must be at least 8 characters", "Le mot de passe doit contenir au moins 8 caractères"),
    PasswordSpecialCharacter("Password must contain special characters", "Le mot de passe doit contenir des caractères spéciaux"),
    PasswordNumber("Password must contain numbers", "Le mot de passe doit contenir des chiffres"),
    PasswordCapitalLetter("Password must contain capital letters", "Le mot de passe doit contenir des lettres majuscules"),
    UsernameNotFound("username not found", "nom d'utilisateur introuvable"),
    IncorrectPassword("Password is incorrect", "le mot de passe est incorrect"),
    IncorrectAnswer("Wrong answer", "Le mot de passe est incorrect"),
    SamePassword("The new password must be different from the previous password", "Le nouveau mot de passe doit être différent du mot de passe précédent"),
    // Security Questions
    FatherName("What is your father name?", "quel est le nom de ton père"),
    Turk("Are you Turk?", "Es tu Turk?"),
    // labels
    Username("Username", "Nom d'utilisateur"),
    MusicVolume("Music volume", "Volume de la musique"),
    MusicTrack("Music track", "Morceau de musique"),
    KeyBinds("Key binds", "Raccourcis clavier"),
    Up("Up", "Haut"),
    Down("Down", "Bas"),
    Right("Right", "Droite"),
    Left("Left", "Gauche"),
    Reload("Reload", "Recharger"),
    Shoot("Shoot", "Tirer"),
    SelectWeapon("Select weapon", "Sélectionner une arme"),
    SelectHero("Select hero", "Sélectionner le héros"),
    GameDuration("Game duration: ", "Durée du jeu: "),
    SelectGameDuration("Select game duration", "Sélectionner la durée du jeu"),
    Vitality("Increases HP by one", "Augmente les PV d'un"),
    Damager("Increases weapon damage by 25% for 10 seconds", "Augmente les dégâts de l'arme de 25 % pendant 10 secondes"),
    Procrease("Increases weapon projectile by one", "Augmente le projectile de l'arme d'un"),
    Ammocrease("Increases weapon's ammo limit by five", "Augmente la limite de munitions de l'arme de cinq"),
    Speedy("Doubles player's speed for 10 seconds", "Double la vitesse du joueur pendant 10 secondes"),
    Level("Level ", "Niveau "),
    YouWon("You Won", "Tu as gagné"),
    YouLost("You Lost", "Tu as perdu"),
    TimeSurvived("The amount of time you survived: ", "Le temps pendant lequel vous avez survécu: "),
    Kills("Kills", "Victimes"),
    PointsEarned("Points earned: ", "Points gagnés: "),
    Score("Score", "Score"),
    MaxSurvivedTime("Max Survived Time", "Temps de survie maximal"),
    SortedBy("Sorted by", "Trié par"),
    BossFightStarted("Boss Fight Started", "Le combat contre le boss a commencé"),
    // check box
    BlackAndWhite("Black and white", "Noir et Blanc"),
    AutoReload("Auto Reload", "Rechargement Automatique"),
    // cheat codes
    DecreaseTime("Decreases remaining time by one minute", "Diminue le temps restant d'une minute"),
    IncreaseLevel("Increases player level", "Augmente le niveau du joueur"),
    IncreaseHp("Increases player HP by one", "Augmente les PV du joueur d'un"),
    GoToBossFight("Go to boss fight", "Aller au combat de boss"),
    DestroyMonsters("destroys all monsters except elder and trees", "détruit tous les monstres sauf Elder et les arbres\n");

    private final String english;
    private final String french;

    Output(String english, String french) {
        this.english = english;
        this.french = french;
    }

    public String getString() {
        return App.isFrench()? french : english;
    }

    public static Output getPhrase(String phrase) {
        for (Output output : Output.values()) {
            if (output.english.equalsIgnoreCase(phrase) || output.french.equalsIgnoreCase(phrase))
                return output;
        }
        return null;
    }
}
