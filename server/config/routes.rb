Rails.application.routes.draw do
  root to: "top#index"
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html

  resource :auth, controller: :auth, only: [] do
    get :login
    post :execute
    post :logout
  end

  resources :dj_room, only: [:index,:show,:new,:create] do
    member do
      post :join
    end
    collection do
      get :playing
    end
  end
end
