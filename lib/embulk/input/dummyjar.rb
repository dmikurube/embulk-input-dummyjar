Embulk::JavaPlugin.register_input(
  "dummyjar", "org.embulk.input.dummyjar.DummyjarInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
