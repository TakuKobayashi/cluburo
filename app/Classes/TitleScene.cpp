//
//  TitleScene.cpp
//  cluburo
//
//  Created by 小林 拓 on 2017/03/11.
//
//

#include "TitleScene.hpp"

#include "HelloWorldScene.h"
#include "SimpleAudioEngine.h"

USING_NS_CC;

Scene* TitleScene::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();
    
    // 'layer' is an autorelease object
    auto layer = TitleScene::create();
    
    // add layer as a child to scene
    scene->addChild(layer);
    
    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool TitleScene::init()
{
    
    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }
    
    auto visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();
    
    auto background_sprite = Sprite::create("startscene/back_color_black.png");
    background_sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));
    this->addChild(background_sprite, 0);
    
    auto bgstart_sprite = Sprite::create("startscene/background_start.png");
    bgstart_sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));
    this->addChild(bgstart_sprite, 1);

    auto logo_sprite = Sprite::create("startscene/logo.png");
    logo_sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height - 160 + origin.y));
    this->addChild(logo_sprite, 1);

    auto icon_sprite = Sprite::create("startscene/icon.png");
    icon_sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, 230 + origin.y));
    this->addChild(icon_sprite, 1);
    
    auto start_button = Sprite::create("startscene/button_start.png");
    auto start_button_menu = MenuItemSprite::create(start_button, start_button, [this](Ref* sender){
        //シーン移動
        Director::getInstance()->replaceScene(HelloWorld::createScene());
    });
    //メニューの作成　pMenuの中にpBtnItemを入れる
    auto menu = Menu::create(start_button_menu, NULL);
    
    //pMenuを画面中央に配置
    menu->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));
    this->addChild(menu, 2);

    
/*
    std::string fullPath = FileUtils::getInstance()->fullPathForFilename("MainScene.csb");
    log("--------------------------------------------------%s", fullPath.c_str());
    auto mainScene = CSLoader::getInstance()->createNode("MainScene.csb");
    this->addChild(mainScene);
    
    // ボタンノードを取得
    auto startButton = mainScene->getChildByName<ui::ImageView*>("start_button");
    log("---------------------------------------------------------------%d", startButton->isVisible());
    // タッチイベント追加
    startButton->addClickEventListener([this](Ref* sender){
        log("---------------------------------------------------------------");
        //シーン移動
        Director::getInstance()->replaceScene(HelloWorld::createScene());
    });
 */

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.
    
    /////////////////////////////
    // 3. add your codes below...
    
    // add a label shows "Hello World"
    // create and initialize a label
    return true;
}
