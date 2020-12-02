<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\post;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class PostController extends Controller
{
    public function news()
    {
        $results = [];
        foreach (post::latest('id')->paginate() as $post) {
            $results[] = [
                'id' => $post->id,
                'title' => $post->title,
                'price' => $post->price,
                'image' => $post->image,
            ];
        }
        if ($results != null) {
            return response()->json([
                'result' => $results
            ]);
        }else{
            return ['result'=> 'not data'];
        }
    }
    Public function amazing(){
        $results = [];
        foreach (post::where('amazing', '=', 'true')->latest('id')->paginate() as $post) {
            $results[] = [
                'id' => $post->id,
                'title' => $post->title,
                'price' => $post->price,
                'image' => $post->image,
            ];
        }
        if ($results != null) {
            return response()->json([
                'result' => $results
            ]);
        }else{
            return ['result'=> 'not data'];
        }
    }
    public function detail($id){
        $results=post::find($id);
        return response()->json([
            'result' => $results
        ]);
    }
    Public function search($field,$value){
        $results = [];
        foreach (post::where($field, 'like', "%".$value."%")->latest('id')->paginate() as $post) {
            $results[] = [
                'id' => $post->id,
                'title' => $post->title,
                'price' => $post->price,
                'image' => $post->image,
            ];
        }
        if ($results != null) {
            return response()->json([
                'result' => $results
            ]);
        }else{
            return ['result'=> 'not data'];
        }
    }
}
