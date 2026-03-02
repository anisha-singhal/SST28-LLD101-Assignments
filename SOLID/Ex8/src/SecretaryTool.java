public class SecretaryTool implements MinutesOperations {
    private final MinutesBook minutes;

    public SecretaryTool(MinutesBook minutes) { this.minutes = minutes; }

    @Override
    public void addMinutes(String text) { minutes.add(text); }
}
