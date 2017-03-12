//
//  TitleScene.hpp
//  cluburo
//
//  Created by 小林 拓 on 2017/03/11.
//
//

#ifndef TitleScene_hpp
#define TitleScene_hpp

#include "cocos2d.h"
#include "ui/CocosGUI.h"
#include "editor-support/cocostudio/CocoStudio.h"

class TitleScene : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();
    
    virtual bool init();
    
    // implement the "static create()" method manually
    CREATE_FUNC(TitleScene);
};

#endif /* TitleScene_hpp */
