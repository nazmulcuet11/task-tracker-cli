use clap::{Parser, Subcommand};

#[derive(Parser)]
#[command(name = "task-cli", about = "A simple task tracker")]
struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
  Add { 
    #[arg(num_args = 1.., trailing_var_arg = true)]
    description: Vec<String> 
  },
  List,
}

fn main() {
    let cli = Cli::parse();

    match cli.command {
        Commands::Add { description } => {
            let task_name = description.join(", ");
            println!("Adding task: {task_name}");
        }
        Commands::List => {
            println!("Listing tasks...");
        }
    }
}
