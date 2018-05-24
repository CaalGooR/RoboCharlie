
public class LocalScope extends BaseScope {
	private Scope local;
	public LocalScope (Scope local) { this.local = local; }
	public Scope returnLocalScope() { return local; }
}
