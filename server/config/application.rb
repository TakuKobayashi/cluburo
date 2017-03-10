require_relative 'boot'

# DB を使わないので一旦コメントアウト
#require 'rails/all'

require "rails"      
require "active_job/railtie"        
require "action_controller/railtie"     
require "action_mailer/railtie"     
require "action_view/railtie"       
require "action_cable/engine"       
require "sprockets/railtie"       
require "rails/test_unit/railtie"

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

module Server
  class Application < Rails::Application
    # Settings in config/environments/* take precedence over those specified here.
    # Application configuration should go into files in config/initializers
    # -- all .rb files in that directory are automatically loaded.
  end
end
