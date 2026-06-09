use clap::{Parser, Subcommand};

#[derive(Parser)]
#[command(name = "task-cli", about = "A simple task tracker")]
struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
  Add { description: String },
  List,
}

fn main() {
    let cli = Cli::parse();
    
    match cli.command {
        Commands::Add { description } => {
            println!("Adding task: {description}");
        }
        Commands::List => {
            println!("Listing tasks...");
        }
    }
}
