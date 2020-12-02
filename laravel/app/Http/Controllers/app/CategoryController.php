<?php

namespace App\Http\Controllers\app;

use App\Http\Controllers\Controller;
use App\Models\post;
use Illuminate\Http\Request;
use App\Models\category;
class CategoryController extends Controller
{
    public function parent()
    {
        $results = [];
        foreach (category::where('parent_id', '=', 0)->latest('id')->paginate() as $cat) {
            $results[] = [
                'id' => $cat->id,
                'title' => $cat->title,
                'image' => $cat->image,
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

    public function child($parent_id)
    {
        $results = [];
        foreach (category::where('parent_id', '=', $parent_id)->latest('id')->paginate() as $cat) {
            $results[] = [
                'id' => $cat->id,
                'title' => $cat->title,
                'image' => $cat->image,
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

    public function category_carpet($id_category)
    {
        $results = [];
        foreach (post::where('id_category', '=', $id_category)->latest('id')->paginate() as $cat) {
            $results[] = [
                'id' => $cat->id,
                'title' => $cat->title,
                'price'=> $cat->price,
                'image' => $cat->image,
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
