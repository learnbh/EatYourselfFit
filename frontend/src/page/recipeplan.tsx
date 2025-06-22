import type {Ingredient} from "../types.ts";
import AddRecipe_layout from "../layout/addRecipe_layout.tsx";
import IngredientRecipe from "../component/IngredientRecipe.tsx";

type Props = {
    dishname:string
    ingredients:Ingredient[]
    handleChangeDishName: (dishname:string) => void
    addIngredientToRecipe: (ingredient:Ingredient) => void
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
    handleQuantity: (ingredient:Ingredient) => void
}

export default function Recipeplan(props:Readonly<Props>){


    function handleChangeDishName(dishName:string){
        props.handleChangeDishName(dishName);
    }

    function addIngredientToRecipe(ingredient:Ingredient){
        props.addIngredientToRecipe(ingredient);
    }

    function removeIngredientFromRecipe(ingredient:Ingredient){
        props.removeIngredientFromRecipe(ingredient);
    }

    function handleQuantity(ingredient:Ingredient){
        props.handleQuantity(ingredient);
    }

    function saveRecipe(){

    }

    return(
        <>
            <div>
                <h1>Hier kannst Du ein neues Gericht erstellen</h1>
                <div className="recipeplan">
                    <div>
                        <AddRecipe_layout
                            handleChangeDishName={handleChangeDishName}
                            addIngredientToRecipe={addIngredientToRecipe}
                            removeIngredientFromRecipe={removeIngredientFromRecipe}
                            handleQuantity={handleQuantity}
                        />
                    </div>
                    <div className="flex flex-col border rounded shadow p-2 font-serif h-full w-96">
                        <h2 className="text-xl">{props.dishname}</h2>
                        <div className="flex-1 overflow-auto my-4">
                            {props.ingredients.map(i =>
                                <IngredientRecipe
                                    key = {i.id}
                                    ingredient={i}
                                    removeIngredientFromRecipe={removeIngredientFromRecipe}
                                />)
                            }
                        </div>
                        <button className="border p-2 font-sans mt-auto" type="submit" onClick={saveRecipe}>
                            Rezept Speichern
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}