Rails.application.routes.draw do
  root to: "top#index"
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html

  resource :auth, controller: :auth, only: [] do
    get :login
    post :execute
    post :logout
  end

  resource :dj_room, only: [:show,:new,:create] do
    get :detail
    post :join
    get :playing
  end
end
