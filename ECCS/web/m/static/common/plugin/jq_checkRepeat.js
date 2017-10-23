/**
 * Copyright (c) 2011 - 2015,social-touch Inc. All rights reserved.
 * @fileoverview social-touch  阻止频繁点击方法
 * @author  weijialu | @social-touch.com
 * @version 1.0 | 2015-09-21
 * @param
 * @example
 */
define(function(require, exports, module) {
    var checkRepeat = function( self , tiems ){
            var repeat = self.data( 'repeat' ),
                tiems = tiems ? tiems : 2000;
            if( repeat ){
                return true;
            }
            self.data( 'repeat', true );
            setTimeout( function(){ self.data( 'repeat', '' ) }, tiems );
            return false;
        }

    return checkRepeat;

});