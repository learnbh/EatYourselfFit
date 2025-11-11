import {GiCook, GiHotMeal} from "react-icons/gi";
import {GrSchedule, GrScheduleNew} from "react-icons/gr";
import { GiCorn } from "react-icons/gi";
import { FaUser } from "react-icons/fa6";
import { CgProfile } from "react-icons/cg";
import TooltipLink from "./TooltipLink.tsx";
import {MainNavNames} from "../enums.ts";
import { useUser } from "../context/user/useUser.tsx";

export default function NavIcons(){    

    const userContext = useUser();
    const user = userContext?.user;

    return (
            <div className="nav-links open">
                <TooltipLink
                    tooltipText={ MainNavNames.Recipe }
                    to = "/recipe"
                    element={ <GiCook style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.CreateRecipe }
                    to = "/recipeplan"
                    element={ <GiHotMeal style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Ingredients }
                    to = "/ingredient"
                    element={ <GiCorn style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Weekplan }
                    to = "/weekplan"
                    element={ <GrSchedule style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
                <TooltipLink
                    tooltipText={ MainNavNames.Shoppinglist }
                    to = "/shoppinglist"
                    element={ <GrScheduleNew style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-6"
                />
                <TooltipLink
                    tooltipText={ user?MainNavNames.Profil : MainNavNames.Login }
                    to ={ user?"/profile":"/login"}
                    element={ user?<CgProfile style = {{ width: "inherit", height:"inherit" }} />  : <FaUser style = {{ width: "inherit", height:"inherit" }} />  }
                    class="h-5"
                />
            </div>
    );
}